# Files.com Java Client

The Files.com Java client library provides convenient access to the Files.com API from JVM based applications.


## Installation


### Maven

A maven jar is available through [maven-central](https://search.maven.org/).
To use the package add the following to your `pom.xml` file.

```xml
    <dependency>
        <groupId>com.files</groupId>
        <artifactId>files-sdk</artifactId>
    </dependency>
```


### Gradle

To add the dependency to your Gradle project add this to your

```groovy
    compile group: 'com.files', name: 'files-sdk'
```


### Requirements

The Files.com Java SDK supports all versions of Java beginning with Java 8 (Also known as 1.8).


## Usage


### Authentication

There are multiple ways to authenticate to the Files.com SDK for Java.


#### Global API Key

You can set an API key globally, like this:

```java
    FilesClient.apiKey = "my-key";
```


#### Per-Request API Key

Or, you can pass an API key per-request, in the Options HashMap at the end
of every method.  Like this:

```java
    HashMap<String, Object> requestOptions = new HashMap<>();
    requestOptions.put("api_key", "my-key");
    List<User> users = User.list(null, requestOptions).all();
```

That key will automatically be used for any followup actions that occur
on models returned from the API.


#### User Session

Or, you can open a user session by calling `Session.create()`
```java
    HashMap<String, Object> sessionParameters = new HashMap<>();
    sessionParameters.put("username", "username");
    sessionParameters.put("password", "password");
    Session session = Session.create(sessionParameters);
```

Then use it as follows:
```java
    HashMap<String, Object> requestOptions = new HashMap<>();
    requestOptions.put("session_id", session.getId());
    List<User> users = User.list(null, requestOptions).all();
```

Or use if for all subsequent API calls globally like this:
```java
    FilesClient.session = session;
```


### Setting Global Options

You can set the following global options directly on the `FilesClient` module:
```java
    FilesClient.setProperty("apiRoot", "https://files-mock-server:4041");
```

### Pagination

For endpoints with pagination, operations such as `list` will return a `ListIterator` object. This object allows for accessing pages of
results with `loadNextPage()`, `all()`, and auto-pagination using `listAutoPaging()`.


### Error Handling

Unexpected errors when attempting to connect to the API inherit from the base level `SdkException` class. They all contain a `getMessage()`
to describe what went wrong.


#### Unable to connect to the API

```java
try {
    Folder.ListFor("/").all();
} catch (ApiErrorException.ApiConnectionException e) {
    System.out.println("Unable to list root folder: " + e.getMessage());
}
```

Errors from the API inherit from `ApiErrorException.ApiException`. They all contain more parameters to describe the error such as `getHttpCode`, `GetError`, `getDetail`, etc.


#### Path does not exist

```java
try {
    Folder.ListFor("/doesnotexist").all();
} catch (ApiErrorException.NotFoundException e) {
    System.out.println("Unable to list folder: <" + e.getHttpStatus() + "> " + e.getError());
}
```


### File Operations

#### List root folder (loads all pages into memory)

```java
    Folder.listFor("/", null).all()
```


#### List root folder with auto pagination (loads each page into memory)

```java
    for (Folder item : Folder.listFor("/", null).listAutoPaging()) {
        System.out.println(item.path);
    }
```


#### List root folder with manual pagination (loads each page into memory)

```java
    ListIterator<Folder> listing = Folder.listFor("/", null);
    do {
        for (Folder item : listing.loadNextPage()) {
            System.out.println(item.path);
        }
    } while (listing.hasNextPage());
```


#### Writing a file example

```java
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
```


#### Reading a file's text as a InputStream

```java
    File file = File.download("test.txt", null);
    try(InputStream inputStream = file.getInputStream()) {
       String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
         .lines()
         .collect(Collectors.joining("\n"));
         String filecontents = text;
    }
```


#### Reading a file and writing it to your local drive

```java
    File file = File.download("test.txt", null);
    file.saveAsLocalFile("/tmp/");
```


#### Comparing Case insensitive files and paths

For related documentation see [Case Sensitivity Documentation](https://www.files.com/docs/files-and-folders/file-system-semantics/case-sensitivity).

```java
    if (PathUtils.isSame("Fïłèńämê.Txt", "filename.txt")) {
        System.out.println("Paths are the same");
    }
```


### Logging

The Files.com SDK is compatible with the standard log4j logging scheme.


#### Adding `com.files` logger to your `Loggers` root in the `log4j2.xml` file

```xml
<Loggers>
    <!-- set preferred level -->
    <Logger name="com.files" level="TRACE" />
    <!-- to enable network request -->
    <Logger name="okhttp3.logging.wire" level="INFO"/>
</Loggers>
```


#### Creating a `resources/log4j2.xml` file
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <!-- set preferred level -->
        <Logger name="com.files" level="TRACE"/>
        <!-- to enable network request -->
        <Logger name="okhttp3.logging.wire" level="INFO"/>
    </Loggers>
</Configuration>
```

You can read more about log4j2 configuration [here](https://logging.apache.org/log4j/2.x/manual/configuration.html).


### Additional Object Documentation

Additional docs are available at https://developers.files.com/ and also
in the `docs/` subdirectory of this directory.


## Getting Support

The Files.com team is happy to help with any SDK Integration challenges you
may face.

Just email support@files.com and we'll get the process started.
