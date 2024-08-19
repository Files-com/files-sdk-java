# Files.com Java Client

The content included here should be enough to get started, but please visit our
[Developer Documentation Website](https://developers.files.com/java/) for the complete documentation.

## Introduction

The Files.com Java client library provides convenient access to the Files.com API from JVM based applications.

### Installation

#### Maven

A maven jar is available through [maven-central](https://search.maven.org/).
To use the package add the following to your `pom.xml` file.

```xml
<dependency>
    <groupId>com.files</groupId>
    <artifactId>files-sdk</artifactId>
</dependency>
```

#### Gradle

To add the dependency to your Gradle project add this to your

```groovy
compile group: 'com.files', name: 'files-sdk'
```

#### Requirements

The Files.com Java SDK supports all versions of Java beginning with Java 8 (Also known as 1.8).

<Note title="Repository">
Explore the [files-sdk-java](https://github.com/Files-com/files-sdk-java) code on GitHub.
</Note>

### Getting Support

The Files.com team is happy to help with any SDK Integration challenges you
may face.

Just email support@files.com and we'll get the process started.

## Authentication

### Authenticate with an API Key

Authenticating with an API key is the recommended authentication method for most scenarios, and is
the method used in the examples on this site.

To use the API or SDKs with an API Key, first generate an API key from the [web
interface](https://www.files.com/docs/sdk-and-apis/api-keys) or [via the API or an
SDK](/java/resources/developers/api-keys).

Note that when using a user-specific API key, if the user is an administrator, you will have full
access to the entire API. If the user is not an administrator, you will only be able to access files
that user can access, and no access will be granted to site administration functions in the API.

```java title="Example Request"
FilesClient.apiKey = "YOUR_API_KEY";
// Alternatively, you can specify the API key on a per-object
// basis in options HashMap to a model constructor.

HashMap<String, Object> requestOptions = new HashMap()<>;
requestOptions.put("api_key", "my-key");
User user = new User(params, requestParameters);

// You may also specify the API key on a per-request basis in
// in the final parameter to static methods.
HashMap<String, Object> requestOptions = new HashMap()<>;
requestOptions.put("api_key", "my-key");
User.find(id, params, requestOptions);
```

<Note>
Don't forget to replace the placeholder, `YOUR_API_KEY`, with your actual API key.
</Note>

### Authenticate with a Session

You can also authenticate to the REST API or SDKs by creating a user session using the username and
password of an active user. If the user is an administrator, the session will have full access to
the entire API. Sessions created from regular user accounts will only be able to access files that
user can access, and no access will be granted to site administration functions.

API sessions use the exact same session timeout settings as web interface sessions. When an API
session times out, simply create a new session and resume where you left off. This process is not
automatically handled by SDKs because we do not want to store password information in memory without
your explicit consent.

#### Logging in

To create a session, the `create` method is called on the `Session` object with the user's username and
password.

This returns a session object that can be used to authenticate SDK method calls.

```java title="Example Request"
HashMap<String, Object> sessionParameters = new HashMap<>()
sessionParameters.put("username", "username");
sessionParameters.put("password", "password");
Session session = Session.create(parameters)
```

#### Using a session

Once a session has been created, you can store the session globally, use the session per object, or use the session per request to authenticate SDK operations.

```java title="Example Request"
// You may set the returned session to be used by default for subsequent requests.
FilesClient.session = session;

// Alternatively, you can specify the session ID on a per-object basis
// in the second parameter to a model constructor.
HashMap<String, Object> requestParameters = new HashMap<>();
requestParameters.put("session_id", session.getId());
user = new User(params, requestParameters);

// You may also specify the session ID on a per-request basis in the final parameter to static methods.
HashMap<String, Object> requestParameters = new HashMap<>();
requestParameters.put("session_id", session.getId());
User.find(id, params, requestParameters);

```

#### Logging out

User sessions can be ended calling the `destroy` method on the `session` object.

```java title="Example Request"
session.destroy()
```

## Configuration

### Configuration options

#### Base URL

Setting the base URL for the API is required if your site is configured to disable global acceleration.
This can also be set to use a mock server in development or CI.

```java title="Example setting"
FilesClient.setProperty("apiRoot", "https://MY-SUBDOMAIN.files.com");
```

### Logging

The Files.com SDK is compatible with the standard log4j logging scheme.

Add `com.files` logger to your `Loggers` root in the `log4j2.xml` file.

```xml title="log4j2.xml"
<Loggers>
    <!-- set preferred level -->
    <Logger name="com.files" level="TRACE" />
    <!-- to enable network request -->
    <Logger name="okhttp3.logging.wire" level="INFO"/>
</Loggers>
```

Create a `resources/log4j2.xml` file.

```xml title="resources/log4j2.xml"
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

You can read more about [log4j2 configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html).

## Errors

The Files.com Java SDK will return errors by raising exceptions. There are many exception classes defined in the Files SDK that correspond
to specific errors.

The raised exceptions come from two categories:

1.  SDK Exceptions - errors that originate within the SDK
2.  API Exceptions - errors that occur due to the response from the Files.com API.  These errors are grouped into common error types.

There are several types of exceptions within each category.  Exception classes indicate different types of errors and are named in a
fashion that describe the general premise of the originating error.  More details can be found in the exception object message using the
`getMessage()` method.

Use standard Java exception handling to detect and deal with errors.  It is generally recommended to catch specific errors first, then
catch the general `SdkException` exception as a catch-all.

```java title="Example Error Handling"
package com.filescom.app;
import java.util.HashMap;

import com.files.models.*;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;

public class App
{
    public static void main( String[] args )
    {
        HashMap<String, Object> sessionParameters = new HashMap<>();
        sessionParameters.put("username", "USERNAME");
        sessionParameters.put("password", "BADPASSWORD");

        try{
            Session session = Session.create(sessionParameters);
        }
        catch(NotAuthenticatedException e){
            System.out.println("Authentication Error Occurred (" + e.getClass().getName() +"): " + e.getMessage());
        }
        catch(SdkException e){
            System.out.println("Unknown Error Occurred (" + e.getClass().getName() +"): " + e.getMessage());
        }

        System.out.println( "The End." );
    }
}

```

### Error Types

#### SDK Errors

SDK errors are general errors that occur within the SDK code.  These errors generate exceptions.  Each of these
exception classes inherit from a standard `SdkException` base class.

```shell title="Example SDK Exception Class Inheritance Structure"
com.files.exceptions.ApiErrorException.ApiConnectionException ->
com.files.exceptions.SdkException ->
RuntimeException
```
##### SDK Exception Classes

| Exception Class Name| Description |
| --------------- | ------------ |
| `ApiConnectionException`| The Files.com API cannot be reached |
| `AuthenticationException`| Authentication Failure on the Files.com API |
| `InvalidParameterException`| A passed in parameter is invalid |
| `InvalidResponseException`| A bad formed response came back from the API |
| `ServerErrorException`| The API service responded with a bad response (ie, 5xx) |

#### API Errors

API errors are errors returned by the Files.com API.  Each exception class inherits from an error group base class.
The error group base class indicates a particular type of error.

```shell title="Example API Exception Class Inheritance Structure"
com.files.exceptions.ApiErrorException.FolderAdminPermissionRequiredException ->
com.files.exceptions.ApiErrorException.NotAuthorizedException ->
com.files.exceptions.ApiErrorException ->
com.files.exceptions.SdkException ->
RuntimeException
```
##### API Exception Classes

| Exception Class Name | Error Group |
| --------- | --------- |
|`AgentUpgradeRequiredException`|  `BadRequestException` |
|`AttachmentTooLargeException`|  `BadRequestException` |
|`CannotDownloadDirectoryException`|  `BadRequestException` |
|`CantMoveWithMultipleLocationsException`|  `BadRequestException` |
|`DatetimeParseException`|  `BadRequestException` |
|`DestinationSameException`|  `BadRequestException` |
|`FolderMustNotBeAFileException`|  `BadRequestException` |
|`InvalidBodyException`|  `BadRequestException` |
|`InvalidCursorException`|  `BadRequestException` |
|`InvalidCursorTypeForSortException`|  `BadRequestException` |
|`InvalidEtagsException`|  `BadRequestException` |
|`InvalidFilterAliasCombinationException`|  `BadRequestException` |
|`InvalidFilterCombinationException`|  `BadRequestException` |
|`InvalidFilterFieldException`|  `BadRequestException` |
|`InvalidFilterParamException`|  `BadRequestException` |
|`InvalidFilterParamValueException`|  `BadRequestException` |
|`InvalidInputEncodingException`|  `BadRequestException` |
|`InvalidInterfaceException`|  `BadRequestException` |
|`InvalidOauthProviderException`|  `BadRequestException` |
|`InvalidPathException`|  `BadRequestException` |
|`InvalidReturnToUrlException`|  `BadRequestException` |
|`InvalidUploadOffsetException`|  `BadRequestException` |
|`InvalidUploadPartGapException`|  `BadRequestException` |
|`InvalidUploadPartSizeException`|  `BadRequestException` |
|`MethodNotAllowedException`|  `BadRequestException` |
|`NoValidInputParamsException`|  `BadRequestException` |
|`PartNumberTooLargeException`|  `BadRequestException` |
|`PathCannotHaveTrailingWhitespaceException`|  `BadRequestException` |
|`ReauthenticationNeededFieldsException`|  `BadRequestException` |
|`RequestParamsContainInvalidCharacterException`|  `BadRequestException` |
|`RequestParamsInvalidException`|  `BadRequestException` |
|`RequestParamsRequiredException`|  `BadRequestException` |
|`SearchAllOnChildPathException`|  `BadRequestException` |
|`UnsupportedCurrencyException`|  `BadRequestException` |
|`UnsupportedHttpResponseFormatException`|  `BadRequestException` |
|`UnsupportedMediaTypeException`|  `BadRequestException` |
|`UserIdInvalidException`|  `BadRequestException` |
|`UserIdOnUserEndpointException`|  `BadRequestException` |
|`UserRequiredException`|  `BadRequestException` |
|`AdditionalAuthenticationRequiredException`|  `NotAuthenticatedException` |
|`AuthenticationRequiredException`|  `NotAuthenticatedException` |
|`BundleRegistrationCodeFailedException`|  `NotAuthenticatedException` |
|`FilesAgentTokenFailedException`|  `NotAuthenticatedException` |
|`InboxRegistrationCodeFailedException`|  `NotAuthenticatedException` |
|`InvalidCredentialsException`|  `NotAuthenticatedException` |
|`InvalidOauthException`|  `NotAuthenticatedException` |
|`InvalidOrExpiredCodeException`|  `NotAuthenticatedException` |
|`InvalidSessionException`|  `NotAuthenticatedException` |
|`InvalidUsernameOrPasswordException`|  `NotAuthenticatedException` |
|`LockedOutException`|  `NotAuthenticatedException` |
|`LockoutRegionMismatchException`|  `NotAuthenticatedException` |
|`OneTimePasswordIncorrectException`|  `NotAuthenticatedException` |
|`TwoFactorAuthenticationErrorException`|  `NotAuthenticatedException` |
|`TwoFactorAuthenticationSetupExpiredException`|  `NotAuthenticatedException` |
|`ApiKeyIsDisabledException`|  `NotAuthorizedException` |
|`ApiKeyIsPathRestrictedException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForDesktopAppException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForMobileAppException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForOfficeIntegrationException`|  `NotAuthorizedException` |
|`BillingOrSiteAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`BillingPermissionRequiredException`|  `NotAuthorizedException` |
|`BundleMaximumUsesReachedException`|  `NotAuthorizedException` |
|`CannotLoginWhileUsingKeyException`|  `NotAuthorizedException` |
|`CantActForOtherUserException`|  `NotAuthorizedException` |
|`ContactAdminForPasswordChangeHelpException`|  `NotAuthorizedException` |
|`FilesAgentFailedAuthorizationException`|  `NotAuthorizedException` |
|`FolderAdminOrBillingPermissionRequiredException`|  `NotAuthorizedException` |
|`FolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`FullPermissionRequiredException`|  `NotAuthorizedException` |
|`HistoryPermissionRequiredException`|  `NotAuthorizedException` |
|`InsufficientPermissionForParamsException`|  `NotAuthorizedException` |
|`InsufficientPermissionForSiteException`|  `NotAuthorizedException` |
|`MustAuthenticateWithApiKeyException`|  `NotAuthorizedException` |
|`NeedAdminPermissionForInboxException`|  `NotAuthorizedException` |
|`NonAdminsMustQueryByFolderOrPathException`|  `NotAuthorizedException` |
|`NotAllowedToCreateBundleException`|  `NotAuthorizedException` |
|`PasswordChangeNotRequiredException`|  `NotAuthorizedException` |
|`PasswordChangeRequiredException`|  `NotAuthorizedException` |
|`ReadOnlySessionException`|  `NotAuthorizedException` |
|`ReadPermissionRequiredException`|  `NotAuthorizedException` |
|`ReauthenticationFailedException`|  `NotAuthorizedException` |
|`ReauthenticationFailedFinalException`|  `NotAuthorizedException` |
|`ReauthenticationNeededActionException`|  `NotAuthorizedException` |
|`RecaptchaFailedException`|  `NotAuthorizedException` |
|`SelfManagedRequiredException`|  `NotAuthorizedException` |
|`SiteAdminRequiredException`|  `NotAuthorizedException` |
|`SiteFilesAreImmutableException`|  `NotAuthorizedException` |
|`TwoFactorAuthenticationRequiredException`|  `NotAuthorizedException` |
|`UserIdWithoutSiteAdminException`|  `NotAuthorizedException` |
|`WriteAndBundlePermissionRequiredException`|  `NotAuthorizedException` |
|`WritePermissionRequiredException`|  `NotAuthorizedException` |
|`ZipDownloadIpMismatchException`|  `NotAuthorizedException` |
|`ApiKeyNotFoundException`|  `NotFoundException` |
|`BundlePathNotFoundException`|  `NotFoundException` |
|`BundleRegistrationNotFoundException`|  `NotFoundException` |
|`CodeNotFoundException`|  `NotFoundException` |
|`FileNotFoundException`|  `NotFoundException` |
|`FileUploadNotFoundException`|  `NotFoundException` |
|`FolderNotFoundException`|  `NotFoundException` |
|`GroupNotFoundException`|  `NotFoundException` |
|`InboxNotFoundException`|  `NotFoundException` |
|`NestedNotFoundException`|  `NotFoundException` |
|`PlanNotFoundException`|  `NotFoundException` |
|`SiteNotFoundException`|  `NotFoundException` |
|`UserNotFoundException`|  `NotFoundException` |
|`AlreadyCompletedException`|  `ProcessingFailureException` |
|`AutomationCannotBeRunManuallyException`|  `ProcessingFailureException` |
|`BehaviorNotAllowedOnRemoteServerException`|  `ProcessingFailureException` |
|`BundleOnlyAllowsPreviewsException`|  `ProcessingFailureException` |
|`BundleOperationRequiresSubfolderException`|  `ProcessingFailureException` |
|`CouldNotCreateParentException`|  `ProcessingFailureException` |
|`DestinationExistsException`|  `ProcessingFailureException` |
|`DestinationFolderLimitedException`|  `ProcessingFailureException` |
|`DestinationParentConflictException`|  `ProcessingFailureException` |
|`DestinationParentDoesNotExistException`|  `ProcessingFailureException` |
|`ExpiredPrivateKeyException`|  `ProcessingFailureException` |
|`ExpiredPublicKeyException`|  `ProcessingFailureException` |
|`ExportFailureException`|  `ProcessingFailureException` |
|`ExportNotReadyException`|  `ProcessingFailureException` |
|`FailedToChangePasswordException`|  `ProcessingFailureException` |
|`FileLockedException`|  `ProcessingFailureException` |
|`FileNotUploadedException`|  `ProcessingFailureException` |
|`FilePendingProcessingException`|  `ProcessingFailureException` |
|`FileProcessingErrorException`|  `ProcessingFailureException` |
|`FileTooBigToDecryptException`|  `ProcessingFailureException` |
|`FileTooBigToEncryptException`|  `ProcessingFailureException` |
|`FileUploadedToWrongRegionException`|  `ProcessingFailureException` |
|`FilenameTooLongException`|  `ProcessingFailureException` |
|`FolderLockedException`|  `ProcessingFailureException` |
|`FolderNotEmptyException`|  `ProcessingFailureException` |
|`HistoryUnavailableException`|  `ProcessingFailureException` |
|`InvalidBundleCodeException`|  `ProcessingFailureException` |
|`InvalidFileTypeException`|  `ProcessingFailureException` |
|`InvalidFilenameException`|  `ProcessingFailureException` |
|`InvalidPriorityColorException`|  `ProcessingFailureException` |
|`InvalidRangeException`|  `ProcessingFailureException` |
|`ModelSaveErrorException`|  `ProcessingFailureException` |
|`MultipleProcessingErrorsException`|  `ProcessingFailureException` |
|`PathTooLongException`|  `ProcessingFailureException` |
|`RecipientAlreadySharedException`|  `ProcessingFailureException` |
|`RemoteServerErrorException`|  `ProcessingFailureException` |
|`ResourceLockedException`|  `ProcessingFailureException` |
|`SubfolderLockedException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationCodeAlreadySentException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationCountryBlacklistedException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationGeneralErrorException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationUnsubscribedRecipientException`|  `ProcessingFailureException` |
|`UpdatesNotAllowedForRemotesException`|  `ProcessingFailureException` |
|`DuplicateShareRecipientException`|  `RateLimitedException` |
|`ReauthenticationRateLimitedException`|  `RateLimitedException` |
|`TooManyConcurrentLoginsException`|  `RateLimitedException` |
|`TooManyConcurrentRequestsException`|  `RateLimitedException` |
|`TooManyLoginAttemptsException`|  `RateLimitedException` |
|`TooManyRequestsException`|  `RateLimitedException` |
|`TooManySharesException`|  `RateLimitedException` |
|`AgentUnavailableException`|  `ServiceUnavailableException` |
|`AutomationsUnavailableException`|  `ServiceUnavailableException` |
|`MigrationInProgressException`|  `ServiceUnavailableException` |
|`SiteDisabledException`|  `ServiceUnavailableException` |
|`UploadsUnavailableException`|  `ServiceUnavailableException` |
|`AccountAlreadyExistsException`|  `SiteConfigurationException` |
|`AccountOverdueException`|  `SiteConfigurationException` |
|`NoAccountForSiteException`|  `SiteConfigurationException` |
|`SiteWasRemovedException`|  `SiteConfigurationException` |
|`TrialExpiredException`|  `SiteConfigurationException` |
|`TrialLockedException`|  `SiteConfigurationException` |
|`UserRequestsEnabledRequiredException`|  `SiteConfigurationException` |

## Examples

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

#### Writing a file

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
            System.out.println("TransferredSize:"+transferred.getSize());
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
        String fileContents = text;
}
```

#### Reading a file and writing it to your local drive

```java
File file = File.download("test.txt", null);
file.saveAsLocalFile("/tmp/");
```

### Miscellaneous

#### Comparing Case insensitive files and paths

For related documentation see [Case Sensitivity Documentation](https://www.files.com/docs/files-and-folders/file-system-semantics/case-sensitivity).

```java
if (PathUtils.isSame("Fïłèńämê.Txt", "filename.txt")) {
    System.out.println("Paths are the same");
}
```

## Mock Server

Files.com publishes a Files.com API server, which is useful for testing your use of the Files.com
SDKs and other direct integrations against the Files.com API in an integration test environment.

It is a Ruby app that operates as a minimal server for the purpose of testing basic network
operations and JSON encoding for your SDK or API client. It does not maintain state and it does not
deeply inspect your submissions for correctness.

Eventually we will add more features intended for integration testing, such as the ability to
intentionally provoke errors.

Download the server as a Docker image via [Docker Hub](https://hub.docker.com/r/filescom/files-mock-server).

The Source Code is also available on [GitHub](https://github.com/Files-com/files-mock-server).

A README is available on the GitHub link.
