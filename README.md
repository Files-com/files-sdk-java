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

Set this to the full https:// URL of your Files.com subdomain (e.g. `https://MY-SUBDOMAIN.files.com`).
This is not required in most cases, but one benefit of setting it is that it ensures that authentication failures will be logged to your site's API logs.  Without setting this, we won't know which site to associate the authentication failure with, and it won't be logged to your site's API logs.
This is always required if your site is configured to disable global acceleration.
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

Files.com preserves the spelling of file and folder paths while comparing them using shared case and Unicode rules. Use the SDK comparison helpers when matching paths locally.
<div></div>

### Capitalization

Files.com uses case-insensitive path matching based on its fixed Unicode comparison map.

For example, the following paths have the same comparison key:

| Path Variant                          | Comparison Key              |
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

Use `/` between folder and file names, without leading or trailing slashes. SDK normalization helpers convert backslashes to `/`, remove duplicate separators, and discard exact `.` and `..` components. Discarding `..` leaves the preceding folder name intact.

| Input | Normalized path |
|-------|-----------------|
| `folder/subfolder/file.txt` | `folder/subfolder/file.txt` |
| `/folder/subfolder/file.txt` | `folder/subfolder/file.txt` |
| `folder/subfolder/file.txt/` | `folder/subfolder/file.txt` |
| `//folder//file.txt` | `folder/file.txt` |
| `folder/../file.txt` | `folder/file.txt` |

<div></div>

### Unicode and Path Comparison

Files.com compares paths using a fixed mapping shared by the server and SDKs. It treats case and many accent differences as equivalent: `Résumé.txt` and `resume.txt` identify the same file, as do `q` followed by a combining acute accent and `q`. The mapping also handles other equivalences, such as Hiragana and Katakana. Lowercasing or applying a standard Unicode normalization form alone does not reproduce these rules.

SDK comparison helpers normalize path separators and dot segments, then apply the bundled [versioned comparison map](https://github.com/Files-com/files-sdk-javascript/blob/master/shared/path_comparison.json). The [shared examples](https://github.com/Files-com/files-sdk-javascript/blob/master/shared/comparison_examples.json) give exact comparison results for integrations that implement their own matching. The map uses hexadecimal Unicode scalar values as keys: a missing entry preserves the character, an empty replacement removes it, and other replacements may contain several characters. Apply each replacement once without normalizing or lowercasing the result again.

Use comparison results only for matching. Send the original path spelling in API requests and preserve it for display and local filenames; comparison results can have a different spelling or length.

Trailing whitespace is significant for comparison. `report.txt` and `report.txt ` are different file paths, and SDK helpers preserve spaces, tabs, and newlines. Folder names cannot end in whitespace. See [Unicode Normalization](https://www.files.com/docs/files-and-folders/file-system-semantics/unicode-normalization) for the complete path rules.

<div></div>

## Workspaces

A Workspace groups files, users, groups, Partners, integrations, and workflows within a Files.com Site. An integration can provision a Workspace for a department or project and delegate its operation to a team without making that team Site Administrators. Every Site has a Default Workspace, with ID `0`; additional Workspaces have their own IDs and root folders.

Account membership, request context, and permission grants serve different purposes. Creating an account in a Workspace determines where it belongs. Selecting a Workspace determines which resources a request operates on. A permission grant determines what the caller can do there. Selecting a Workspace never grants access to it.

### Accounts and Administrative Access

A user's or group's `workspace_id` identifies the Workspace the account belongs to. Accounts belonging to a Custom Workspace stay within it. Default Workspace users and groups can receive permissions in one or more Custom Workspaces while keeping their existing accounts in Workspace `0`.

| Account | Workspace Administrator assignment | Scope |
| --- | --- | --- |
| User belonging to a Custom Workspace | Set the user's `workspace_admin` to `true`. | That user's own Custom Workspace. |
| Default Workspace user | Create an `admin` Permission for the user on a Custom Workspace's root folder. | Each Custom Workspace with a root grant. |
| Default Workspace group | Create an `admin` Permission for the group on a Custom Workspace's root folder. | Every member inherits administration of each Workspace with a root grant. |

`workspace_admin` is not a summary of a user's effective administrative access. A Default Workspace user can administer a Custom Workspace through a direct or group root grant while their `workspace_admin` remains `false`. Groups have no `workspace_admin` field. See [Users](/java/resources/user-accounts/users) and [Groups](/java/resources/user-accounts/groups) for account fields.

An `admin` grant on the **Custom Workspace root** provides full Workspace Administrator authority over its files, users, groups, Partners, workflows, and integrations. An `admin` grant on a subfolder provides Folder Admin authority over that folder and its descendants; it does not provide Workspace administration. Other permission levels provide their corresponding folder access without Workspace administration. [Permissions](/java/resources/user-accounts/permissions) defines the levels.

Site Administrators manage cross-Workspace assignments to Default Workspace accounts. Workspace Administrators manage accounts and permissions within their own scope. Site Administrators retain access to every Workspace; adding a Workspace grant does not narrow Site Administrator authority. The [product documentation](https://www.files.com/docs/workspaces/workspace-administrators) explains the role's operational scope and site-wide controls.

### Request Context and API Keys

The REST header `X-Files-Workspace-Id` selects a Workspace for a request. SDK request options and CLI configuration send that same selection. Workspace-scoped resources are listed, created, and changed within the selected context, and ordinary paths are relative to its root.

A resource's `workspace_id` request field describes the resource's Workspace membership. It is separate from the SDK's Workspace request option or REST header. Creating a Workspace-scoped resource in a Custom Workspace defaults its `workspace_id` to the selected Workspace; a mismatching membership value is rejected with `not-authorized/insufficient-permission-for-params`.

Selecting another Workspace with an API key requires a **Full Access key created in the Default Workspace**. A user key follows that user's current access, including group permissions. A site-wide Full Access key created in the Default Workspace has Site Administrator authority in every Workspace. A Files Only key stays in its creation Workspace, even if its user has cross-Workspace access. Any key created in a Custom Workspace stays within that Workspace. Selecting another context with these confined keys is rejected with `bad-request/invalid-workspace-id-header`.

An account belonging to a Custom Workspace is scoped there when it authenticates normally. For a Default Workspace user, explicitly select the intended Workspace for an integration rather than relying on an interactive login preference. [API Keys](/java/resources/developers/api-keys) and [Authentication](/java/overview/authentication) cover credentials.

The Files.com Java SDK supports workspace scoping by using the `FilesClient.workspaceId` configuration attribute. Scope a single request by passing `workspace_id` in the request options.

The adjacent scoping example uses a credential authorized for the selected Workspace. A group member uses their own Full Access user key from the Default Workspace; the Site Administrator credential used to assign the grant is not needed for their day-to-day work.

```java title="Example Request"
import com.files.FilesClient;
import com.files.models.Folder;
import java.util.HashMap;

FilesClient.workspaceId = 123L;

HashMap<String, Object> options = new HashMap<>();
options.put("workspace_id", 456L);

Folder.listFor("/", null, options);
```

### Delegating a Workspace to an Existing Group

An operations team already represented by a Default Workspace group can administer a Custom Workspace through one root Permission. The group and its members stay in the Default Workspace, so the same team can receive different access in other Workspaces.

First retrieve the target [Workspace](/java/resources/settings/workspaces) and [Group](/java/resources/user-accounts/groups) IDs as a Site Administrator in Workspace `0`. The examples use Workspace `123`, group `456`, and member user `789`; replace them with your own IDs. Confirm that the group belongs to Workspace `0` and that the intended user is a member.

Create the Permission using a Default Workspace Full Access site-wide key or a Full Access user key belonging to a Site Administrator. Keep the request context at `0` and use the qualified root path `_/Workspaces/123`. Set `group_id` to the group's ID, `permission` to `admin`, and `recursive` to `true`. Save the returned Permission `id` for later removal. For an individual Default Workspace user, use `user_id` instead of `group_id`.

For a Default Workspace group, a Site Administrator can also select Workspace `123` and use an empty `path` to grant access to its root. The qualified path in Workspace `0` works for both Default Workspace users and groups and keeps the account scope and target Workspace explicit. Appending a subfolder to the path would grant Folder Admin access instead of Workspace Administrator authority.

After the grant, run the request-context example above with the member's own credential and Workspace `123` selected. That member can work with the Workspace's files and perform Workspace Administrator operations, such as managing its users, Partners, and integrations. A Site Administrator's successful request does not establish that the member has the intended access.

```java title="Grant group administration"
import com.files.FilesClient;
import com.files.models.Permission;
import java.util.HashMap;

FilesClient.workspaceId = 0L;
HashMap<String, Object> params = new HashMap<>();
params.put("path", "_/Workspaces/123");
params.put("group_id", 456L);
params.put("permission", "admin");
params.put("recursive", true);
HashMap<String, Object> options = new HashMap<>();
options.put("workspace_id", 0L);
Permission grant = Permission.create(params, options);
System.out.println(grant.getId());
```

### Permission Inspection and Removal

List the member's Permissions with `user_id` and `include_groups=true` to include grants inherited through group membership. Listing only direct user grants can miss the Permission that provides Workspace administration. In Workspace `0`, the Custom Workspace root appears as `_/Workspaces/123`; in Workspace `123`, paths are relative to that root. Inspect the root path and `permission=admin`, rather than treating the user's `workspace_admin` field as their effective role.

Permission lists show grants, rather than a single effective-role boolean. Membership in several groups combines their access. A Permission using `group_ids` instead of `group_id` requires membership in all the specified groups; it is not a shorthand for assigning the same grant to several independent groups.

Removing a member ends access received through that group. Deleting the root Permission ends the group's Workspace Administrator grant for every member. These changes leave independent direct and other group grants in place, so review all applicable grants when withdrawing access. Default Workspace user API keys follow those permission changes without being recreated.

Group membership maintained through SCIM follows the same rule. A Group Admin allowed to add members can give those users the group's existing Workspace Administrator access. Choose who manages the group with that authority in mind.

Delete the Permission by its returned `id` as the Site Administrator in Workspace `0`. The removal examples use Permission ID `9001`; replace it with the ID returned by your create request. Permissions are created and deleted, rather than updated in place. If narrower folder access is still needed, assign it explicitly; deleting a broad grant does not restore narrower grants it previously replaced.

```java title="Inspect member grants and remove the group grant"
HashMap<String, Object> query = new HashMap<>();
query.put("user_id", "789");
query.put("include_groups", true);
var grants = Permission.list(query, options);
Permission.delete(9001L, null, options);
```

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
|`DestinationSiteMismatchException`|  `BadRequestException` |
|`DoesNotSupportSortingException`|  `BadRequestException` |
|`FolderMustNotBeAFileException`|  `BadRequestException` |
|`FoldersNotAllowedException`|  `BadRequestException` |
|`InternalGeneralErrorException`|  `BadRequestException` |
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
|`InvalidSearchQueryException`|  `BadRequestException` |
|`InvalidSortFieldException`|  `BadRequestException` |
|`InvalidSortFilterCombinationException`|  `BadRequestException` |
|`InvalidUploadOffsetException`|  `BadRequestException` |
|`InvalidUploadPartGapException`|  `BadRequestException` |
|`InvalidUploadPartSizeException`|  `BadRequestException` |
|`InvalidWorkspaceIdHeaderException`|  `BadRequestException` |
|`MethodNotAllowedException`|  `BadRequestException` |
|`MultipleSortParamsNotAllowedException`|  `BadRequestException` |
|`NoValidInputParamsException`|  `BadRequestException` |
|`OffsetUploadNotAllowedWithMalwareScanningException`|  `BadRequestException` |
|`PartNumberTooLargeException`|  `BadRequestException` |
|`PathCannotHaveTrailingWhitespaceException`|  `BadRequestException` |
|`ReauthenticationNeededFieldsException`|  `BadRequestException` |
|`RequestBodyTooLargeException`|  `BadRequestException` |
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
|`ApiKeyOnlyForFileOperationsException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForMobileAppException`|  `NotAuthorizedException` |
|`ApiKeyOnlyForOfficeIntegrationException`|  `NotAuthorizedException` |
|`BillingInformationHiddenException`|  `NotAuthorizedException` |
|`BillingPermissionRequiredException`|  `NotAuthorizedException` |
|`BundleMaximumUsesReachedException`|  `NotAuthorizedException` |
|`BundlePermissionRequiredException`|  `NotAuthorizedException` |
|`CannotAdministerHigherLevelUserException`|  `NotAuthorizedException` |
|`CannotLoginWhileUsingKeyException`|  `NotAuthorizedException` |
|`CantActForOtherUserException`|  `NotAuthorizedException` |
|`ContactAdminForPasswordChangeHelpException`|  `NotAuthorizedException` |
|`FilesAgentFailedAuthorizationException`|  `NotAuthorizedException` |
|`FolderAdminOrBillingPermissionRequiredException`|  `NotAuthorizedException` |
|`FolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`FullPermissionRequiredException`|  `NotAuthorizedException` |
|`HistoryPermissionRequiredException`|  `NotAuthorizedException` |
|`InAppAiAssistantUnavailableException`|  `NotAuthorizedException` |
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
|`PreviewOnlyPermissionCannotDownloadException`|  `NotAuthorizedException` |
|`ReadOnlySessionException`|  `NotAuthorizedException` |
|`ReadPermissionRequiredException`|  `NotAuthorizedException` |
|`ReauthenticationFailedException`|  `NotAuthorizedException` |
|`ReauthenticationFailedFinalException`|  `NotAuthorizedException` |
|`ReauthenticationNeededActionException`|  `NotAuthorizedException` |
|`RecaptchaFailedException`|  `NotAuthorizedException` |
|`RemoteDesktopDebugLoggingDisabledException`|  `NotAuthorizedException` |
|`RootFolderBehaviorSiteAdminRequiredException`|  `NotAuthorizedException` |
|`RootFolderBehaviorSkipSiteAdminRequiredException`|  `NotAuthorizedException` |
|`SelfManagedRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrPartnerAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminOrFolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminOrPartnerAdminOrFolderAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminOrPartnerAdminPermissionRequiredException`|  `NotAuthorizedException` |
|`SiteAdminOrWorkspaceAdminPermissionRequiredException`|  `NotAuthorizedException` |
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
|`AgentPushUpdateBlockedException`|  `ProcessingFailureException` |
|`AgentUnavailableException`|  `ProcessingFailureException` |
|`AiTaskCannotBeRunManuallyException`|  `ProcessingFailureException` |
|`AlreadyCompletedException`|  `ProcessingFailureException` |
|`AutomationCannotBeRunManuallyException`|  `ProcessingFailureException` |
|`BehaviorNotAllowedOnRemoteServerException`|  `ProcessingFailureException` |
|`BufferedUploadDisabledForThisDestinationException`|  `ProcessingFailureException` |
|`BundleOnlyAllowsPreviewsException`|  `ProcessingFailureException` |
|`BundleOperationRequiresSubfolderException`|  `ProcessingFailureException` |
|`ConfigurationLockedPathException`|  `ProcessingFailureException` |
|`CouldNotCreateParentException`|  `ProcessingFailureException` |
|`DestinationExistsException`|  `ProcessingFailureException` |
|`DestinationFolderLimitedException`|  `ProcessingFailureException` |
|`DestinationParentConflictException`|  `ProcessingFailureException` |
|`DestinationParentDoesNotExistException`|  `ProcessingFailureException` |
|`ExceededRuntimeLimitException`|  `ProcessingFailureException` |
|`ExpectationAlreadyHasOpenWindowException`|  `ProcessingFailureException` |
|`ExpectationNotManualTriggerException`|  `ProcessingFailureException` |
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
|`InvalidZipFileException`|  `ProcessingFailureException` |
|`MetadataNotSupportedOnRemotesException`|  `ProcessingFailureException` |
|`ModelSaveErrorException`|  `ProcessingFailureException` |
|`MultipleProcessingErrorsException`|  `ProcessingFailureException` |
|`PathTooLongException`|  `ProcessingFailureException` |
|`RecipientAlreadySharedException`|  `ProcessingFailureException` |
|`RemoteEntryReadOnlyException`|  `ProcessingFailureException` |
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
|`SearchUnavailableException`|  `ServiceUnavailableException` |
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
