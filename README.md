# Files.com Java Client

The Files.com Java Client provides a direct, high performance integration to Files.com from applications written in Java.

Files.com is the cloud-native, next-gen MFT, SFTP, and secure file-sharing platform that replaces brittle legacy servers with one always-on, secure fabric. Automate mission-critical file flows—across any cloud, protocol, or partner—while supporting human collaboration and eliminating manual work.

With universal SFTP, AS2, HTTPS, and 50+ native connectors backed by military-grade encryption, Files.com unifies governance, visibility, and compliance in a single pane of glass.

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

### Files.com is Committed to Java

Java is a core language used by the Files.com team for internal development.  This library is directly used by our official Boomi integration and our official MuleSoft integration.

As such, this library is actively developed and should be expected to be highly performant.

Explore the [files-sdk-java](https://github.com/Files-com/files-sdk-java) code on GitHub.

### Getting Support

The Files.com Support team provides official support for all of our official Files.com integration tools.

To initiate a support conversation, you can send an [Authenticated Support Request](https://www.files.com/docs/overview/requesting-support) or simply send an E-Mail to support@files.com.

## Authentication

There are two ways to authenticate: API Key authentication and Session-based authentication.

### Authenticate with an API Key

Authenticating with an API key is the recommended authentication method for most scenarios, and is
the method used in the examples on this site.

To use an API Key, first generate an API key from the [web
interface](https://www.files.com/docs/sdk-and-apis/api-keys) or [via the API or an
SDK](/java/resources/developers/api-keys).

Note that when using a user-specific API key, if the user is an administrator, you will have full
access to the entire API. If the user is not an administrator, you will only be able to access files
that user can access, and no access will be granted to site administration functions in the API.

```java title="Example Request"
import com.files.FilesClient;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

FilesClient.apiKey = "YOUR_API_KEY";

try {
  // Alternatively, you can specify the API key on a per-object basis in options HashMap to a model constructor.
  HashMap<String, Object> requestOptions = new HashMap<>();
  requestOptions.put("api_key", "my-key");
  User user = new User(params, requestOptions);

  // You may also specify the API key on a per-request basis in the final parameter to static methods.
  HashMap<String, Object> requestOptions = new HashMap<>();
  requestOptions.put("api_key", "my-key");
  User.find(id, params, requestOptions);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

Don't forget to replace the placeholder, `YOUR_API_KEY`, with your actual API key.

### Authenticate with a Session

You can also authenticate by creating a user session using the username and
password of an active user. If the user is an administrator, the session will have full access to
all capabilities of Files.com. Sessions created from regular user accounts will only be able to access files that
user can access, and no access will be granted to site administration functions.

Sessions use the exact same session timeout settings as web interface sessions. When a
session times out, simply create a new session and resume where you left off. This process is not
automatically handled by our SDKs because we do not want to store password information in memory without
your explicit consent.

#### Logging In

To create a session, the `create` method is called on the `Session` object with the user's username and
password.

This returns a session object that can be used to authenticate SDK method calls.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.Session;
import java.util.HashMap;

HashMap<String, Object> sessionParameters = new HashMap<>()
sessionParameters.put("username", "username");
sessionParameters.put("password", "password");

try {
  Session session = Session.create(parameters);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

#### Using a Session

Once a session has been created, you can store the session globally, use the session per object, or use the session per request to authenticate SDK operations.

```java title="Example Request"
import com.files.FilesClient;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// You may set the returned session to be used by default for subsequent requests.
FilesClient.session = session;

try {
  // Alternatively, you can specify the session ID on a per-object basis in the second parameter to a model constructor.
  HashMap<String, Object> requestOptions = new HashMap<>();
  requestOptions.put("session_id", session.getId());
  user = new User(params, requestOptions);

  // You may also specify the session ID on a per-request basis in the final parameter to static methods.
  HashMap<String, Object> requestOptions = new HashMap<>();
  requestOptions.put("session_id", session.getId());
  User.find(id, params, requestOptions);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

#### Logging Out

User sessions can be ended calling the `destroy` method on the `session` object.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;

try {
  session.destroy();
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

## Configuration

Configuration is performed by calling `FilesClient.setProperty()`.

### Configuration Options

#### Base URL

Setting the base URL for the API is required if your site is configured to disable global acceleration.
This can also be set to use a mock server in development or CI.

```java title="Example setting"
import com.files.FilesClient;

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

## Sort and Filter

Several of the Files.com API resources have list operations that return multiple instances of the
resource. The List operations can be sorted and filtered.

### Sorting

To sort the returned data, pass in the ```sort_by``` method argument.

Each resource supports a unique set of valid sort fields and can only be sorted by one field at a
time.

The argument value is a Java ```HashMap<String, Object>``` object that has a property of the
resource field name sort on and a value of either ```"asc"``` or ```"desc"``` to specify the sort
order.

#### Special note about the List Folder Endpoint

For historical reasons, and to maintain compatibility
with a variety of other cloud-based MFT and EFSS services, Folders will always be listed before Files
when listing a Folder.  This applies regardless of the sorting parameters you provide.  These *will* be
used, after the initial sort application of Folders before Files.

```java title="Sort Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users sorted by username
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> sortArgs = new HashMap<>();
sortArgs.put("username", "asc");
args.put("sort_by", sortArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Filtering

Filters apply selection criteria to the underlying query that returns the results. They can be
applied individually or combined with other filters, and the resulting data can be sorted by a
single field.

Each resource supports a unique set of valid filter fields, filter combinations, and combinations of
filters and sort fields.

The passed in argument value is a Java ```HashMap<String, Object>``` object that has a key of the
resource field name to filter on and a passed in value to use in the filter comparison.

#### Filter Types

| Filter | Type | Description |
| --------- | --------- | --------- |
| `filter` | Exact | Find resources that have an exact field value match to a passed in value. (i.e., FIELD_VALUE = PASS_IN_VALUE). |
| `filter_prefix` | Pattern | Find resources where the specified field is prefixed by the supplied value. This is applicable to values that are strings. |
| `filter_gt` | Range | Find resources that have a field value that is greater than the passed in value.  (i.e., FIELD_VALUE > PASS_IN_VALUE). |
| `filter_gteq` | Range | Find resources that have a field value that is greater than or equal to the passed in value.  (i.e., FIELD_VALUE >=  PASS_IN_VALUE). |
| `filter_lt` | Range | Find resources that have a field value that is less than the passed in value.  (i.e., FIELD_VALUE < PASS_IN_VALUE). |
| `filter_lteq` | Range | Find resources that have a field value that is less than or equal to the passed in value.  (i.e., FIELD_VALUE \<= PASS_IN_VALUE). |

```java title="Exact Filter Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// non admin users
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
filterArgs.put("not_site_admin", true);
args.put("filter", filterArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

```java title="Range Filter Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users who haven't logged in since 2024-01-01
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
filterArgs.put("last_login_at", "2024-01-01");
args.put("filter_gteq", filterArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

```java title="Pattern Filter Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users whose usernames start with 'test'
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
filterArgs.put("username", "test");
args.put("filter_prefix", filterArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

```java title="Combination Filter with Sort Example"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.User;
import java.util.HashMap;

// users whose usernames start with 'test' and are not admins
HashMap<String, Object> args = new HashMap<>();
HashMap<String, Object> filterPrefixArgs = new HashMap<>();
HashMap<String, Object> filterArgs = new HashMap<>();
HashMap<String, Object> sortArgs = new HashMap<>();
filterPrefixArgs.put("username", "test");
filterArgs.put("not_site_admin", true);
sortArgs.put("last_login_at", "asc");
args.put("filter_prefix", filterPrefixArgs);
args.put("filter", filterArgs);
args.put("sort_by", sortArgs);

try {
  ListIterator<User> users = User.list(args);
  for (User user : users.listAutoPaging()) {
    // Operate on user
    System.out.println(user.username);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

## Paths

Working with paths in Files.com involves several important considerations. Understanding how path comparisons are applied helps developers ensure consistency and accuracy across all interactions with the platform.
<div></div>

### Capitalization

Files.com compares paths in a **case-insensitive** manner. This means path segments are treated as equivalent regardless of letter casing.

For example, all of the following resolve to the same internal path:

| Path Variant                          | Interpreted As              |
|---------------------------------------|------------------------------|
| `Documents/Reports/Q1.pdf`            | `documents/reports/q1.pdf`  |
| `documents/reports/q1.PDF`            | `documents/reports/q1.pdf`  |
| `DOCUMENTS/REPORTS/Q1.PDF`            | `documents/reports/q1.pdf`  |

This behavior applies across:
- API requests
- Folder and file lookup operations
- Automations and workflows

See also: [Case Sensitivity Documentation](https://www.files.com/docs/files-and-folders/case-sensitivity/)

The `PathUtils.isSame` function in the Files.com SDK is designed to help you determine if two paths on
your native file system would be considered the same on Files.com. This is particularly important
when handling errors related to duplicate file names and when developing tools for folder
synchronization.

```java title="Compare Case-Insensitive Files and Paths"
import com.files.util.PathUtils;

if (PathUtils.isSame("Fïłèńämê.Txt", "filename.txt")) {
    System.out.println("Paths are the same");
}
```

### Slashes

All path parameters in Files.com (API, SDKs, CLI, automations, integrations) must **omit leading and trailing slashes**. Paths are always treated as **absolute and slash-delimited**, so only internal `/` separators are used and never at the start or end of the string.

####  Path Slash Examples
| Path                              | Valid? | Notes                         |
|-----------------------------------|--------|-------------------------------|
| `folder/subfolder/file.txt`       |   ✅   | Correct, internal separators only |
| `/folder/subfolder/file.txt`      |   ❌   | Leading slash not allowed     |
| `folder/subfolder/file.txt/`      |   ❌   | Trailing slash not allowed    |
| `//folder//file.txt`              |   ❌   | Duplicate separators not allowed |

<div></div>

### Unicode Normalization

Files.com normalizes all paths using [Unicode NFC (Normalization Form C)](https://www.unicode.org/reports/tr15/#Norm_Forms) before comparison. This ensures consistency across different representations of the same characters.

For example, the following two paths are treated as equivalent after NFC normalization:

| Input                                  | Normalized Form       |
|----------------------------------------|------------------------|
| `uploads/\u0065\u0301.txt`             | `uploads/é.txt`        |
| `docs/Café/Report.txt`                 | `docs/Café/Report.txt` |

- All input must be UTF‑8 encoded.
- Precomposed and decomposed characters are unified.
- This affects search, deduplication, and comparisons across SDKs.

<div></div>

## Foreign Language Support

The Files.com Java SDK supports localized responses by using the `FilesClient.language` configuration attribute.
When configured, this guides the API in selecting a preferred language for applicable response content.

Language support currently applies to select human-facing fields only, such as notification messages
and error descriptions.

If the specified language is not supported or the value is omitted, the API defaults to English.

```shell title="Example Request"
import com.files.FilesClient;

FilesClient.language = "es";
```

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
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.*;
import java.util.HashMap;

HashMap<String, Object> sessionParameters = new HashMap<>();
sessionParameters.put("username", "USERNAME");
sessionParameters.put("password", "BADPASSWORD");

try {
  Session session = Session.create(sessionParameters);
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
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
|`DoesNotSupportSortingException`|  `BadRequestException` |
|`FolderMustNotBeAFileException`|  `BadRequestException` |
|`FoldersNotAllowedException`|  `BadRequestException` |
|`InvalidBodyException`|  `BadRequestException` |
|`InvalidCursorException`|  `BadRequestException` |
|`InvalidCursorTypeForSortException`|  `BadRequestException` |
|`InvalidEtagsException`|  `BadRequestException` |
|`InvalidFilterAliasCombinationException`|  `BadRequestException` |
|`InvalidFilterFieldException`|  `BadRequestException` |
|`InvalidFilterParamException`|  `BadRequestException` |
|`InvalidFilterParamFormatException`|  `BadRequestException` |
|`InvalidFilterParamValueException`|  `BadRequestException` |
|`InvalidInputEncodingException`|  `BadRequestException` |
|`InvalidInterfaceException`|  `BadRequestException` |
|`InvalidOauthProviderException`|  `BadRequestException` |
|`InvalidPathException`|  `BadRequestException` |
|`InvalidReturnToUrlException`|  `BadRequestException` |
|`InvalidSortFieldException`|  `BadRequestException` |
|`InvalidSortFilterCombinationException`|  `BadRequestException` |
|`InvalidUploadOffsetException`|  `BadRequestException` |
|`InvalidUploadPartGapException`|  `BadRequestException` |
|`InvalidUploadPartSizeException`|  `BadRequestException` |
|`MethodNotAllowedException`|  `BadRequestException` |
|`MultipleSortParamsNotAllowedException`|  `BadRequestException` |
|`NoValidInputParamsException`|  `BadRequestException` |
|`PartNumberTooLargeException`|  `BadRequestException` |
|`PathCannotHaveTrailingWhitespaceException`|  `BadRequestException` |
|`ReauthenticationNeededFieldsException`|  `BadRequestException` |
|`RequestParamsContainInvalidCharacterException`|  `BadRequestException` |
|`RequestParamsInvalidException`|  `BadRequestException` |
|`RequestParamsRequiredException`|  `BadRequestException` |
|`SearchAllOnChildPathException`|  `BadRequestException` |
|`UnrecognizedSortIndexException`|  `BadRequestException` |
|`UnsupportedCurrencyException`|  `BadRequestException` |
|`UnsupportedHttpResponseFormatException`|  `BadRequestException` |
|`UnsupportedMediaTypeException`|  `BadRequestException` |
|`UserIdInvalidException`|  `BadRequestException` |
|`UserIdOnUserEndpointException`|  `BadRequestException` |
|`UserRequiredException`|  `BadRequestException` |
|`AdditionalAuthenticationRequiredException`|  `NotAuthenticatedException` |
|`ApiKeySessionsNotSupportedException`|  `NotAuthenticatedException` |
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
|`BundlePermissionRequiredException`|  `NotAuthorizedException` |
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
|`MoverAccessDeniedException`|  `NotAuthorizedException` |
|`MoverPackageRequiredException`|  `NotAuthorizedException` |
|`MustAuthenticateWithApiKeyException`|  `NotAuthorizedException` |
|`NeedAdminPermissionForInboxException`|  `NotAuthorizedException` |
|`NonAdminsMustQueryByFolderOrPathException`|  `NotAuthorizedException` |
|`NotAllowedToCreateBundleException`|  `NotAuthorizedException` |
|`NotEnqueuableSyncException`|  `NotAuthorizedException` |
|`PasswordChangeNotRequiredException`|  `NotAuthorizedException` |
|`PasswordChangeRequiredException`|  `NotAuthorizedException` |
|`PaymentMethodErrorException`|  `NotAuthorizedException` |
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
|`ApiKeyNotFoundException`|  `NotFoundException` |
|`BundlePathNotFoundException`|  `NotFoundException` |
|`BundleRegistrationNotFoundException`|  `NotFoundException` |
|`CodeNotFoundException`|  `NotFoundException` |
|`FileNotFoundException`|  `NotFoundException` |
|`FileUploadNotFoundException`|  `NotFoundException` |
|`GroupNotFoundException`|  `NotFoundException` |
|`InboxNotFoundException`|  `NotFoundException` |
|`NestedNotFoundException`|  `NotFoundException` |
|`PlanNotFoundException`|  `NotFoundException` |
|`SiteNotFoundException`|  `NotFoundException` |
|`UserNotFoundException`|  `NotFoundException` |
|`AgentUnavailableException`|  `ProcessingFailureException` |
|`AlreadyCompletedException`|  `ProcessingFailureException` |
|`AutomationCannotBeRunManuallyException`|  `ProcessingFailureException` |
|`BehaviorNotAllowedOnRemoteServerException`|  `ProcessingFailureException` |
|`BufferedUploadDisabledForThisDestinationException`|  `ProcessingFailureException` |
|`BundleOnlyAllowsPreviewsException`|  `ProcessingFailureException` |
|`BundleOperationRequiresSubfolderException`|  `ProcessingFailureException` |
|`CouldNotCreateParentException`|  `ProcessingFailureException` |
|`DestinationExistsException`|  `ProcessingFailureException` |
|`DestinationFolderLimitedException`|  `ProcessingFailureException` |
|`DestinationParentConflictException`|  `ProcessingFailureException` |
|`DestinationParentDoesNotExistException`|  `ProcessingFailureException` |
|`ExceededRuntimeLimitException`|  `ProcessingFailureException` |
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
|`InvalidSiteException`|  `ProcessingFailureException` |
|`MetadataNotSupportedOnRemotesException`|  `ProcessingFailureException` |
|`ModelSaveErrorException`|  `ProcessingFailureException` |
|`MultipleProcessingErrorsException`|  `ProcessingFailureException` |
|`PathTooLongException`|  `ProcessingFailureException` |
|`RecipientAlreadySharedException`|  `ProcessingFailureException` |
|`RemoteServerErrorException`|  `ProcessingFailureException` |
|`ResourceBelongsToParentSiteException`|  `ProcessingFailureException` |
|`ResourceLockedException`|  `ProcessingFailureException` |
|`SubfolderLockedException`|  `ProcessingFailureException` |
|`SyncInProgressException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationCodeAlreadySentException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationCountryBlacklistedException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationGeneralErrorException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationMethodUnsupportedErrorException`|  `ProcessingFailureException` |
|`TwoFactorAuthenticationUnsubscribedRecipientException`|  `ProcessingFailureException` |
|`UpdatesNotAllowedForRemotesException`|  `ProcessingFailureException` |
|`DuplicateShareRecipientException`|  `RateLimitedException` |
|`ReauthenticationRateLimitedException`|  `RateLimitedException` |
|`TooManyConcurrentLoginsException`|  `RateLimitedException` |
|`TooManyConcurrentRequestsException`|  `RateLimitedException` |
|`TooManyLoginAttemptsException`|  `RateLimitedException` |
|`TooManyRequestsException`|  `RateLimitedException` |
|`TooManySharesException`|  `RateLimitedException` |
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

## Pagination

Certain API operations return lists of objects. When the number of objects in the list is large,
the API will paginate the results.

The Files.com Java SDK provides multiple ways to paginate through lists of objects.

### Automatic Pagination

The `listAutoPaging` function automatically paginates and loads each page into memory.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;

try {
  for (File item : Folder.listFor(path, null).listAutoPaging()) {
    System.out.println(item.path);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Manual Pagination

The `loadNextPage/hasNextPage` functions allow for manual pagination and loading of each page into memory.

```java title="Example Request"
import com.files.ListIterator;
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;

try {
  ListIterator<File> listing = Folder.listFor(path, null);
  do {
    for (File item : listing.loadNextPage()) {
      System.out.println(item.path);
    }
  } while (listing.hasNextPage());
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
}
```

### Load All Items

The `all` function loads all items into memory.

```java title="Example Request"
import com.files.exceptions.*;
import com.files.exceptions.ApiErrorException.*;
import com.files.models.File;
import com.files.models.Folder;
import java.util.List;

try {
  List<File> items = Folder.listFor(path, null).all()
  for (File item : items) {
    System.out.println(item.path);
  }
} catch (NotAuthenticatedException e) {
  System.out.println("Authentication Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
} catch (SdkException e) {
  System.out.println("Unknown Error Occurred (" + e.getClass().getName() + "): " + e.getMessage());
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
