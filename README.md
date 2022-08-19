# Files.com Java Client

The Files.com Java client library provides convenient access to the Files.com API from JVM based applications.


## Installation

### Maven

A maven jar is available through [maven-central](https://search.maven.org/).
To use the package add the following to your `pom.xml` file.

    <dependency>
        <groupId>com.files</groupId>
        <artifactId>files-sdk</artifactId>
        <version>$VERSION</version>
    </dependency>


### Gradle

To add the dependency to your Gradle project add this to your

    compile group: 'com.files', name: 'files-sdk', version: '$VERSION'


### Requirements

* Java 1.8+


## Usage

### Authentication

There are multiple ways to authenticate to the API.


#### Global API Key

You can set an API key globally, like this:

    FilesClient.apiKey = "my-key";


#### Per-Request API Key

Or, you can pass an API key per-request, in the Options HashMap at the end
of every method.  Like this:

    HashMap<String, Object> requestParameters = new HashMap<>();
    requestParameters.put("api_key", "my-key");
    Users.list(requestParameters);

That key will automatically be used for any followup actions that occur
on models returned from the API.


#### User Session

Or, you can open a user session by calling `Session.create()`

    HashMap<String, Object> sessionParameters = new HashMap<>();
    sessionParameters.put("username", "username");
    sessionParameters.put("password", "password");
    Session session = Session.create(sessionParameters);

Then use it as follows:

    HashMap<String, Object> requestParameters = new HashMap<>();
    requestParameters.put("session_id", session.getId());
    Users.list(requestParameters);

Or use if for all subsequent API calls globally like this:

    FilesClient.session = session;


### Setting Global Options

You can set the following global options directly on the `FilesClient` module:

    FilesClient.setProperty("apiRoot", "https://files-mock-server:4041");

### File Operations


#### List root folder


    Folder.listFor("/", null)


#### Writing a file example

    // Will upload a file called "test.txt" and print its size

    import com.files.FilesClient;
    import com.files.models.File;
    import java.io.IOException;

    public class App {
        public static void main( String[] args ) {
            FilesClient.apiKey = "YourAPIKeyHere";

            try {
                File transferred = File.create("test.txt", null).putLocalFile("test.txt");
                System.out.println("TransferedSize:"+transferred.getSize());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

#### Reading a file's text as a InputStream

     File file = File.download("test.txt", null);
     try(InputStream inputStream = file.getInputStream()) {
       String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
         .lines()
         .collect(Collectors.joining("\n"));
         String filecontents = text;
       }
     }

#### Reading a file and writing it to your local drive.

    File file = File.download("test.txt", null);
    file.saveAsLocalFile("/tmp/");


### Additional Object Documentation

Additional docs are available at https://developers.files.com/ and also
in the `docs/` subdirectory of this directory.


### JavaDoc Generated Documentation coming in the future

We will be adding JavaDoc formatted documentation with a future release.


## Getting Support

The Files.com team is happy to help with any SDK Integration challenges you
may face.

Just email support@files.com and we'll get the process started.

