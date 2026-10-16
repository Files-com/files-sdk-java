package com.files.net;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.Base64Variants;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The body of an API request whose parameters include an {@link Attachment}.
 *
 * <p>The body is multipart/form-data, the API's form upload, when form fields carry every other
 * parameter exactly. Otherwise it is JSON, with each attachment as the API's file object
 * {@code {"encoded_content": <base64>, "filename": ..., "type": ...}}, so that every parameter keeps
 * its JSON meaning in the same request. Either way the body is streamed from each attachment's
 * prepared content, declares its exact length and sends the same bytes each time it is written.
 */
final class AttachmentBody implements Closeable {
  private static final Logger log = LoggerFactory.getLogger(AttachmentBody.class);

  private static final Base64Variant BASE64 = Base64Variants.MIME_NO_LINEFEEDS;
  private static final ContentType FORM_TEXT = ContentType.create("text/plain", StandardCharsets.UTF_8);
  // The API's form parser percent-decodes an uploaded file's name, unescapes backslashes and keeps
  // only what follows the last slash or backslash.
  private static final Pattern FILENAME_CHANGED_BY_FORMS = Pattern.compile("[%/\\\\]");
  // Brackets structure form field names, and a quote or control character does not survive in one.
  private static final Pattern KEY_CHANGED_BY_FORMS = Pattern.compile("[\\[\\]\"\\p{Cntrl}]");

  private final ObjectMapper mapper;
  private final List<Field> fields;
  private final HttpEntity entity;

  private AttachmentBody(ObjectMapper mapper, List<Field> fields) {
    this.mapper = mapper;
    this.fields = fields;
    this.entity = sentAsForm(fields) ? form() : new JsonEntity(jsonLength());
  }

  /**
   * Reads, or copies, every attachment in the parameters and takes a snapshot of the other
   * parameters, before anything is sent. Returns null when no parameter is an attachment.
   * {@code checkpoint} runs between reads, and throws when the request is cancelled; the copies
   * made so far are then deleted.
   */
  static AttachmentBody prepare(Map<String, Object> parameters, ObjectMapper mapper, Runnable checkpoint)
      throws AttachmentSourceException {
    if (!parameters.values().stream().anyMatch(value -> value instanceof Attachment)) {
      return null;
    }
    List<Field> fields = new ArrayList<>();
    try {
      for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
        String name = parameter.getKey();
        Object value = parameter.getValue();
        if (value instanceof Attachment) {
          Attachment attachment = (Attachment) value;
          String filename = attachment.getFilename() != null ? attachment.getFilename() : name;
          fields.add(new Field(name, null, new FilePart(filename, attachment.getContentType(), attachment.prepare(name, checkpoint))));
        } else {
          fields.add(new Field(name, value == null ? NullNode.getInstance() : mapper.valueToTree(value), null));
        }
      }
      return new AttachmentBody(mapper, fields);
    } catch (Throwable primary) {
      for (Field field : fields) {
        field.closeFile(primary);
      }
      throw primary;
    }
  }

  HttpEntity entity() {
    return entity;
  }

  /** Deletes the SDK's temporary copies. A failure is logged; the request's outcome stands. */
  @Override
  public void close() {
    for (Field field : fields) {
      field.closeFile(null);
    }
  }

  // Rack's form parser turns a null into "" and an empty list into [""], drops an empty object,
  // receives every value inside an object as text and regroups a list of objects. A request with any
  // of those, or with a name or filename it would change, is sent as JSON. An empty key would change:
  // in brackets, as in watermark_value[], it names a list.
  private static boolean sentAsForm(List<Field> fields) {
    for (Field field : fields) {
      boolean exact = formKeepsName(field.name) && (field.file != null
          ? !FILENAME_CHANGED_BY_FORMS.matcher(field.file.filename).find()
          : formCarriesExactly(field.value, false));
      if (!exact) {
        return false;
      }
    }
    return true;
  }

  private static boolean formKeepsName(String name) {
    return !name.isEmpty() && !KEY_CHANGED_BY_FORMS.matcher(name).find();
  }

  private static boolean formCarriesExactly(JsonNode value, boolean nested) {
    switch (value.getNodeType()) {
      case STRING:
        return true;
      case NUMBER:
      case BOOLEAN:
      case BINARY:
        // The API coerces a top-level parameter's text to its declared type, but not text inside an object.
        return !nested;
      case ARRAY:
        if (nested || value.size() == 0) {
          return false;
        }
        for (JsonNode item : value) {
          if (!item.isTextual() && !item.isNumber() && !item.isBoolean()) {
            return false;
          }
        }
        return true;
      case OBJECT:
        if (value.size() == 0) {
          return false;
        }
        for (Map.Entry<String, JsonNode> entry : value.properties()) {
          if (!formKeepsName(entry.getKey()) || !formCarriesExactly(entry.getValue(), true)) {
            return false;
          }
        }
        return true;
      default:
        return false;
    }
  }

  private HttpEntity form() {
    MultipartEntityBuilder form = MultipartEntityBuilder.create()
        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
        .setCharset(StandardCharsets.UTF_8);
    for (Field field : fields) {
      if (field.file != null) {
        form.addPart(FormBodyPartBuilder.create(field.name, new FileBody(field.file))
            .setField(MIME.CONTENT_TYPE, field.file.contentType)
            .build());
      } else {
        addFormFields(form, field.name, field.value);
      }
    }
    return form.build();
  }

  private static void addFormFields(MultipartEntityBuilder form, String name, JsonNode value) {
    if (value.isObject()) {
      for (Map.Entry<String, JsonNode> entry : value.properties()) {
        addFormFields(form, name + "[" + entry.getKey() + "]", entry.getValue());
      }
    } else if (value.isArray()) {
      for (JsonNode item : value) {
        form.addTextBody(name + "[]", item.asText(), FORM_TEXT);
      }
    } else {
      form.addTextBody(name, value.asText(), FORM_TEXT);
    }
  }

  // The body's length, from writing it without the base64 content, whose length follows from the
  // content's: four characters for every three bytes or part of them.
  private long jsonLength() {
    ByteCounter counter = new ByteCounter();
    try {
      writeJson(counter, false);
    } catch (IOException e) {
      throw new RuntimeException("Failed to serialize parameters", e);
    }
    long length = counter.count;
    for (Field field : fields) {
      if (field.file != null) {
        length += (field.file.content.length() + 2) / 3 * 4;
      }
    }
    return length;
  }

  // Writes the JSON body; without content, each encoded_content is written as "". A failure leaves
  // the body incomplete: the generator is neither closed nor flushed, so it cannot finish the JSON.
  private void writeJson(OutputStream out, boolean withContent) throws IOException {
    JsonGenerator json = mapper.createGenerator(out, JsonEncoding.UTF8)
        .disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET)
        .disable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
    json.writeStartObject();
    for (Field field : fields) {
      json.writeFieldName(field.name);
      if (field.file == null) {
        json.writeTree(field.value);
        continue;
      }
      json.writeStartObject();
      json.writeFieldName("encoded_content");
      if (withContent) {
        try (InputStream content = field.file.content.open()) {
          json.writeBinary(BASE64, content, -1);
        }
      } else {
        json.writeBinary(BASE64, new byte[0], 0, 0);
      }
      json.writeStringField("filename", field.file.filename);
      json.writeStringField("type", field.file.contentType);
      json.writeEndObject();
    }
    json.writeEndObject();
    json.close();
  }

  /** One parameter: either its snapshot as JSON or an attachment's prepared file. */
  private static final class Field {
    final String name;
    final JsonNode value;
    final FilePart file;

    Field(String name, JsonNode value, FilePart file) {
      this.name = name;
      this.value = value;
      this.file = file;
    }

    // With a primary failure, a cleanup failure is kept with it; otherwise it is logged. The warning
    // names the attachment and the kind of failure; the failure's message, which contains the local
    // path of the copy, is logged at DEBUG.
    void closeFile(Throwable primary) {
      if (file == null) {
        return;
      }
      try {
        file.content.close();
      } catch (IOException e) {
        if (primary != null) {
          primary.addSuppressed(e);
        } else {
          log.warn("Could not delete the temporary copy of attachment {} ({})", name, e.getClass().getSimpleName());
          log.debug("The temporary copy of attachment {} was not deleted", name, e);
        }
      }
    }
  }

  private static final class FilePart {
    final String filename;
    final String contentType;
    final AttachmentContent content;

    FilePart(String filename, String contentType, AttachmentContent content) {
      this.filename = filename;
      this.contentType = contentType;
      this.content = content;
    }
  }

  /** A multipart file part, read from the attachment's content each time it is written. */
  private static final class FileBody implements ContentBody {
    private final FilePart file;

    FileBody(FilePart file) {
      this.file = file;
    }

    @Override
    public String getFilename() {
      return file.filename;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
      byte[] buffer = new byte[64 * 1024];
      try (InputStream content = file.content.open()) {
        int read;
        while ((read = content.read(buffer)) != -1) {
          out.write(buffer, 0, read);
        }
      }
    }

    @Override
    public long getContentLength() {
      return file.content.length();
    }

    // The part's Content-Type header is the attachment's media type as given; these describe it.
    @Override
    public String getMimeType() {
      int parameters = file.contentType.indexOf(';');
      return (parameters == -1 ? file.contentType : file.contentType.substring(0, parameters)).trim();
    }

    @Override
    public String getMediaType() {
      String mimeType = getMimeType();
      return mimeType.substring(0, mimeType.indexOf('/'));
    }

    @Override
    public String getSubType() {
      String mimeType = getMimeType();
      return mimeType.substring(mimeType.indexOf('/') + 1);
    }

    @Override
    public String getCharset() {
      return null;
    }

    @Override
    public String getTransferEncoding() {
      return MIME.ENC_BINARY;
    }
  }

  /** The JSON body, written again for each attempt. */
  private final class JsonEntity extends AbstractHttpEntity {
    private final long length;

    JsonEntity(long length) {
      this.length = length;
      setContentType(ContentType.APPLICATION_JSON.toString());
    }

    @Override
    public boolean isRepeatable() {
      return true;
    }

    @Override
    public long getContentLength() {
      return length;
    }

    @Override
    public InputStream getContent() {
      throw new UnsupportedOperationException("An attachment request body is streamed; use writeTo");
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
      writeJson(out, true);
    }

    @Override
    public boolean isStreaming() {
      return false;
    }
  }

  private static final class ByteCounter extends OutputStream {
    long count;

    @Override
    public void write(int b) {
      count++;
    }

    @Override
    public void write(byte[] bytes, int offset, int length) {
      count += length;
    }
  }
}
