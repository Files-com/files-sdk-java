package com.files;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.Property;

/**
 * Records what the SDK logs at WARN and above through the tests' Log4j provider, as an application
 * that sets com.files to WARN would see it, or at another level. Closing it restores the default
 * configuration.
 */
final class SdkWarnings extends AbstractAppender implements AutoCloseable {
  private final List<String> events = new CopyOnWriteArrayList<>();
  private final LoggerContext context = (LoggerContext) LogManager.getContext(false);

  SdkWarnings() {
    this(Level.WARN);
  }

  SdkWarnings(Level level) {
    super("sdk-warnings", null, null, true, Property.EMPTY_ARRAY);
    start();
    LoggerConfig sdkLogger = new LoggerConfig("com.files", level, false);
    sdkLogger.addAppender(this, level, null);
    context.getConfiguration().addLogger("com.files", sdkLogger);
    context.updateLoggers();
  }

  /** Each event as its level, logger name, message and, when present, its stack trace. */
  List<String> events() {
    return events;
  }

  @Override
  public void append(LogEvent event) {
    StringBuilder line = new StringBuilder()
        .append(event.getLevel()).append(' ')
        .append(event.getLoggerName()).append(' ')
        .append(event.getMessage().getFormattedMessage());
    if (event.getThrown() != null) {
      StringWriter stack = new StringWriter();
      event.getThrown().printStackTrace(new PrintWriter(stack));
      line.append(' ').append(stack);
    }
    events.add(line.toString());
  }

  @Override
  public void close() {
    context.getConfiguration().removeLogger("com.files");
    context.updateLoggers();
    stop();
  }
}
