package com.files.exceptions;

import com.files.ResponseError;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.http.Header;

public class ApiErrorException extends SdkException {
  private static final long serialVersionUID = -7605776674523623420L;

  public static ApiErrorException forType(String msg, ResponseError responseError, List<Header> headers) {
    String[] errorParts = responseError.type.split("/");
    String errorType = errorParts[errorParts.length - 1];
    String errorClassName = Arrays.stream(errorType.split("-"))
        .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
        .collect(Collectors.joining("")) + "Exception";

    try {
      Constructor constructor = Class.forName("com.files.exceptions.ApiErrorException$" + errorClassName).getConstructor(new Class[]{String.class, ResponseError.class, List.class});
      return (ApiErrorException) constructor.newInstance(msg, responseError, headers);
    } catch (Exception ex) {
      return new ApiErrorException(msg, responseError, headers);
    }
  }

  private final ResponseError responseError;
  private final List<Header> headers;

  private ApiErrorException(String message, ResponseError responseError, List<Header> headers) {
    super(message);
    this.responseError = responseError;
    this.headers = headers;
  }

  public ResponseError getResponseError() {
    return responseError;
  }

  public List<Header> getHeaders() {
    return headers;
  }

  public String getDetail() {
    return responseError.detail;
  }

  public String getError() {
    return responseError.error;
  }

  public String[] getErrors() {
    return responseError.errors;
  }

  public int getHttpCode() {
    return responseError.httpCode;
  }

  public String instance() {
    return responseError.instance;
  }

  public Map<String, String[]> modelErrors() {
    return responseError.modelErrors;
  }

  public String getTitle() {
    return responseError.title;
  }

  public String getType() {
    return responseError.type;
  }

  public Map<String, Object> getData() {
    return responseError.data;
  }

  @Override
  public String toString() {
    return "[" + getType() + "] " + getError();
  }

  public static class ApiConnectionException extends SdkException {
    public ApiConnectionException(String message) {
      super(message);
    }
  }

  public static class AuthenticationException extends SdkException {
    private final List<Header> headers;

    public AuthenticationException(String message, List<Header> headers) {
      super(message);
      this.headers = headers;
    }

    public List<Header> getHeaders() {
      return headers;
    }
  }

  public static class InvalidParameterException extends SdkException {
    public InvalidParameterException(String message) {
      super(message);
    }
  }

  public static class InvalidResponseException extends SdkException {
    public InvalidResponseException(String message) {
      super(message);
    }
  }

  public static class ServerErrorException extends SdkException {
    public ServerErrorException(String message) {
      super(message);
    }
  }

  public static class BadRequestException extends ApiErrorException {
    public BadRequestException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AgentUpgradeRequiredException extends BadRequestException {
    public AgentUpgradeRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AttachmentTooLargeException extends BadRequestException {
    public AttachmentTooLargeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class CannotDownloadDirectoryException extends BadRequestException {
    public CannotDownloadDirectoryException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class CantMoveWithMultipleLocationsException extends BadRequestException {
    public CantMoveWithMultipleLocationsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DatetimeParseException extends BadRequestException {
    public DatetimeParseException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DestinationSameException extends BadRequestException {
    public DestinationSameException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DoesNotSupportSortingException extends BadRequestException {
    public DoesNotSupportSortingException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FolderMustNotBeAFileException extends BadRequestException {
    public FolderMustNotBeAFileException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FoldersNotAllowedException extends BadRequestException {
    public FoldersNotAllowedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidBodyException extends BadRequestException {
    public InvalidBodyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidCursorException extends BadRequestException {
    public InvalidCursorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidCursorTypeForSortException extends BadRequestException {
    public InvalidCursorTypeForSortException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidEtagsException extends BadRequestException {
    public InvalidEtagsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFilterAliasCombinationException extends BadRequestException {
    public InvalidFilterAliasCombinationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFilterFieldException extends BadRequestException {
    public InvalidFilterFieldException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFilterParamException extends BadRequestException {
    public InvalidFilterParamException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFilterParamFormatException extends BadRequestException {
    public InvalidFilterParamFormatException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFilterParamValueException extends BadRequestException {
    public InvalidFilterParamValueException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidInputEncodingException extends BadRequestException {
    public InvalidInputEncodingException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidInterfaceException extends BadRequestException {
    public InvalidInterfaceException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidOauthProviderException extends BadRequestException {
    public InvalidOauthProviderException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidPathException extends BadRequestException {
    public InvalidPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidReturnToUrlException extends BadRequestException {
    public InvalidReturnToUrlException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidSortFieldException extends BadRequestException {
    public InvalidSortFieldException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidSortFilterCombinationException extends BadRequestException {
    public InvalidSortFilterCombinationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidUploadOffsetException extends BadRequestException {
    public InvalidUploadOffsetException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidUploadPartGapException extends BadRequestException {
    public InvalidUploadPartGapException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidUploadPartSizeException extends BadRequestException {
    public InvalidUploadPartSizeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MethodNotAllowedException extends BadRequestException {
    public MethodNotAllowedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MultipleSortParamsNotAllowedException extends BadRequestException {
    public MultipleSortParamsNotAllowedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NoValidInputParamsException extends BadRequestException {
    public NoValidInputParamsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PartNumberTooLargeException extends BadRequestException {
    public PartNumberTooLargeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PathCannotHaveTrailingWhitespaceException extends BadRequestException {
    public PathCannotHaveTrailingWhitespaceException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReauthenticationNeededFieldsException extends BadRequestException {
    public ReauthenticationNeededFieldsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RequestParamsContainInvalidCharacterException extends BadRequestException {
    public RequestParamsContainInvalidCharacterException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RequestParamsInvalidException extends BadRequestException {
    public RequestParamsInvalidException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RequestParamsRequiredException extends BadRequestException {
    public RequestParamsRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SearchAllOnChildPathException extends BadRequestException {
    public SearchAllOnChildPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UnrecognizedSortIndexException extends BadRequestException {
    public UnrecognizedSortIndexException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UnsupportedCurrencyException extends BadRequestException {
    public UnsupportedCurrencyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UnsupportedHttpResponseFormatException extends BadRequestException {
    public UnsupportedHttpResponseFormatException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UnsupportedMediaTypeException extends BadRequestException {
    public UnsupportedMediaTypeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UserIdInvalidException extends BadRequestException {
    public UserIdInvalidException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UserIdOnUserEndpointException extends BadRequestException {
    public UserIdOnUserEndpointException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UserRequiredException extends BadRequestException {
    public UserRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NotAuthenticatedException extends ApiErrorException {
    public NotAuthenticatedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AdditionalAuthenticationRequiredException extends NotAuthenticatedException {
    public AdditionalAuthenticationRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeySessionsNotSupportedException extends NotAuthenticatedException {
    public ApiKeySessionsNotSupportedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AuthenticationRequiredException extends NotAuthenticatedException {
    public AuthenticationRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundleRegistrationCodeFailedException extends NotAuthenticatedException {
    public BundleRegistrationCodeFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FilesAgentTokenFailedException extends NotAuthenticatedException {
    public FilesAgentTokenFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InboxRegistrationCodeFailedException extends NotAuthenticatedException {
    public InboxRegistrationCodeFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidCredentialsException extends NotAuthenticatedException {
    public InvalidCredentialsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidOauthException extends NotAuthenticatedException {
    public InvalidOauthException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidOrExpiredCodeException extends NotAuthenticatedException {
    public InvalidOrExpiredCodeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidSessionException extends NotAuthenticatedException {
    public InvalidSessionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidUsernameOrPasswordException extends NotAuthenticatedException {
    public InvalidUsernameOrPasswordException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class LockedOutException extends NotAuthenticatedException {
    public LockedOutException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class LockoutRegionMismatchException extends NotAuthenticatedException {
    public LockoutRegionMismatchException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class OneTimePasswordIncorrectException extends NotAuthenticatedException {
    public OneTimePasswordIncorrectException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationErrorException extends NotAuthenticatedException {
    public TwoFactorAuthenticationErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationSetupExpiredException extends NotAuthenticatedException {
    public TwoFactorAuthenticationSetupExpiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NotAuthorizedException extends ApiErrorException {
    public NotAuthorizedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeyIsDisabledException extends NotAuthorizedException {
    public ApiKeyIsDisabledException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeyIsPathRestrictedException extends NotAuthorizedException {
    public ApiKeyIsPathRestrictedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeyOnlyForDesktopAppException extends NotAuthorizedException {
    public ApiKeyOnlyForDesktopAppException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeyOnlyForMobileAppException extends NotAuthorizedException {
    public ApiKeyOnlyForMobileAppException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeyOnlyForOfficeIntegrationException extends NotAuthorizedException {
    public ApiKeyOnlyForOfficeIntegrationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BillingOrSiteAdminPermissionRequiredException extends NotAuthorizedException {
    public BillingOrSiteAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BillingPermissionRequiredException extends NotAuthorizedException {
    public BillingPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundleMaximumUsesReachedException extends NotAuthorizedException {
    public BundleMaximumUsesReachedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundlePermissionRequiredException extends NotAuthorizedException {
    public BundlePermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class CannotLoginWhileUsingKeyException extends NotAuthorizedException {
    public CannotLoginWhileUsingKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class CantActForOtherUserException extends NotAuthorizedException {
    public CantActForOtherUserException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ContactAdminForPasswordChangeHelpException extends NotAuthorizedException {
    public ContactAdminForPasswordChangeHelpException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FilesAgentFailedAuthorizationException extends NotAuthorizedException {
    public FilesAgentFailedAuthorizationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FolderAdminOrBillingPermissionRequiredException extends NotAuthorizedException {
    public FolderAdminOrBillingPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FolderAdminPermissionRequiredException extends NotAuthorizedException {
    public FolderAdminPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FullPermissionRequiredException extends NotAuthorizedException {
    public FullPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class HistoryPermissionRequiredException extends NotAuthorizedException {
    public HistoryPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InsufficientPermissionForParamsException extends NotAuthorizedException {
    public InsufficientPermissionForParamsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InsufficientPermissionForSiteException extends NotAuthorizedException {
    public InsufficientPermissionForSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MoverAccessDeniedException extends NotAuthorizedException {
    public MoverAccessDeniedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MoverPackageRequiredException extends NotAuthorizedException {
    public MoverPackageRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MustAuthenticateWithApiKeyException extends NotAuthorizedException {
    public MustAuthenticateWithApiKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NeedAdminPermissionForInboxException extends NotAuthorizedException {
    public NeedAdminPermissionForInboxException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NonAdminsMustQueryByFolderOrPathException extends NotAuthorizedException {
    public NonAdminsMustQueryByFolderOrPathException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NotAllowedToCreateBundleException extends NotAuthorizedException {
    public NotAllowedToCreateBundleException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NotEnqueuableSyncException extends NotAuthorizedException {
    public NotEnqueuableSyncException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PasswordChangeNotRequiredException extends NotAuthorizedException {
    public PasswordChangeNotRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PasswordChangeRequiredException extends NotAuthorizedException {
    public PasswordChangeRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PaymentMethodErrorException extends NotAuthorizedException {
    public PaymentMethodErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReadOnlySessionException extends NotAuthorizedException {
    public ReadOnlySessionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReadPermissionRequiredException extends NotAuthorizedException {
    public ReadPermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReauthenticationFailedException extends NotAuthorizedException {
    public ReauthenticationFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReauthenticationFailedFinalException extends NotAuthorizedException {
    public ReauthenticationFailedFinalException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReauthenticationNeededActionException extends NotAuthorizedException {
    public ReauthenticationNeededActionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RecaptchaFailedException extends NotAuthorizedException {
    public RecaptchaFailedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SelfManagedRequiredException extends NotAuthorizedException {
    public SelfManagedRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SiteAdminRequiredException extends NotAuthorizedException {
    public SiteAdminRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SiteFilesAreImmutableException extends NotAuthorizedException {
    public SiteFilesAreImmutableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationRequiredException extends NotAuthorizedException {
    public TwoFactorAuthenticationRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UserIdWithoutSiteAdminException extends NotAuthorizedException {
    public UserIdWithoutSiteAdminException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class WriteAndBundlePermissionRequiredException extends NotAuthorizedException {
    public WriteAndBundlePermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class WritePermissionRequiredException extends NotAuthorizedException {
    public WritePermissionRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NotFoundException extends ApiErrorException {
    public NotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ApiKeyNotFoundException extends NotFoundException {
    public ApiKeyNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundlePathNotFoundException extends NotFoundException {
    public BundlePathNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundleRegistrationNotFoundException extends NotFoundException {
    public BundleRegistrationNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class CodeNotFoundException extends NotFoundException {
    public CodeNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileNotFoundException extends NotFoundException {
    public FileNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileUploadNotFoundException extends NotFoundException {
    public FileUploadNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class GroupNotFoundException extends NotFoundException {
    public GroupNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InboxNotFoundException extends NotFoundException {
    public InboxNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NestedNotFoundException extends NotFoundException {
    public NestedNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PlanNotFoundException extends NotFoundException {
    public PlanNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SiteNotFoundException extends NotFoundException {
    public SiteNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ProcessingFailureException extends ApiErrorException {
    public ProcessingFailureException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AlreadyCompletedException extends ProcessingFailureException {
    public AlreadyCompletedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AutomationCannotBeRunManuallyException extends ProcessingFailureException {
    public AutomationCannotBeRunManuallyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BehaviorNotAllowedOnRemoteServerException extends ProcessingFailureException {
    public BehaviorNotAllowedOnRemoteServerException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundleOnlyAllowsPreviewsException extends ProcessingFailureException {
    public BundleOnlyAllowsPreviewsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class BundleOperationRequiresSubfolderException extends ProcessingFailureException {
    public BundleOperationRequiresSubfolderException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class CouldNotCreateParentException extends ProcessingFailureException {
    public CouldNotCreateParentException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DestinationExistsException extends ProcessingFailureException {
    public DestinationExistsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DestinationFolderLimitedException extends ProcessingFailureException {
    public DestinationFolderLimitedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DestinationParentConflictException extends ProcessingFailureException {
    public DestinationParentConflictException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DestinationParentDoesNotExistException extends ProcessingFailureException {
    public DestinationParentDoesNotExistException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ExceededRuntimeLimitException extends ProcessingFailureException {
    public ExceededRuntimeLimitException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ExpiredPrivateKeyException extends ProcessingFailureException {
    public ExpiredPrivateKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ExpiredPublicKeyException extends ProcessingFailureException {
    public ExpiredPublicKeyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ExportFailureException extends ProcessingFailureException {
    public ExportFailureException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ExportNotReadyException extends ProcessingFailureException {
    public ExportNotReadyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FailedToChangePasswordException extends ProcessingFailureException {
    public FailedToChangePasswordException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileLockedException extends ProcessingFailureException {
    public FileLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileNotUploadedException extends ProcessingFailureException {
    public FileNotUploadedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FilePendingProcessingException extends ProcessingFailureException {
    public FilePendingProcessingException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileProcessingErrorException extends ProcessingFailureException {
    public FileProcessingErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileTooBigToDecryptException extends ProcessingFailureException {
    public FileTooBigToDecryptException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileTooBigToEncryptException extends ProcessingFailureException {
    public FileTooBigToEncryptException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FileUploadedToWrongRegionException extends ProcessingFailureException {
    public FileUploadedToWrongRegionException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FilenameTooLongException extends ProcessingFailureException {
    public FilenameTooLongException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FolderLockedException extends ProcessingFailureException {
    public FolderLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class FolderNotEmptyException extends ProcessingFailureException {
    public FolderNotEmptyException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class HistoryUnavailableException extends ProcessingFailureException {
    public HistoryUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidBundleCodeException extends ProcessingFailureException {
    public InvalidBundleCodeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFileTypeException extends ProcessingFailureException {
    public InvalidFileTypeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidFilenameException extends ProcessingFailureException {
    public InvalidFilenameException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidPriorityColorException extends ProcessingFailureException {
    public InvalidPriorityColorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidRangeException extends ProcessingFailureException {
    public InvalidRangeException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class InvalidSiteException extends ProcessingFailureException {
    public InvalidSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ModelSaveErrorException extends ProcessingFailureException {
    public ModelSaveErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MultipleProcessingErrorsException extends ProcessingFailureException {
    public MultipleProcessingErrorsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class PathTooLongException extends ProcessingFailureException {
    public PathTooLongException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RecipientAlreadySharedException extends ProcessingFailureException {
    public RecipientAlreadySharedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RemoteServerErrorException extends ProcessingFailureException {
    public RemoteServerErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ResourceBelongsToParentSiteException extends ProcessingFailureException {
    public ResourceBelongsToParentSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ResourceLockedException extends ProcessingFailureException {
    public ResourceLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SubfolderLockedException extends ProcessingFailureException {
    public SubfolderLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SyncInProgressException extends ProcessingFailureException {
    public SyncInProgressException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationCodeAlreadySentException extends ProcessingFailureException {
    public TwoFactorAuthenticationCodeAlreadySentException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationCountryBlacklistedException extends ProcessingFailureException {
    public TwoFactorAuthenticationCountryBlacklistedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationGeneralErrorException extends ProcessingFailureException {
    public TwoFactorAuthenticationGeneralErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationMethodUnsupportedErrorException extends ProcessingFailureException {
    public TwoFactorAuthenticationMethodUnsupportedErrorException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TwoFactorAuthenticationUnsubscribedRecipientException extends ProcessingFailureException {
    public TwoFactorAuthenticationUnsubscribedRecipientException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UpdatesNotAllowedForRemotesException extends ProcessingFailureException {
    public UpdatesNotAllowedForRemotesException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class RateLimitedException extends ApiErrorException {
    public RateLimitedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class DuplicateShareRecipientException extends RateLimitedException {
    public DuplicateShareRecipientException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ReauthenticationRateLimitedException extends RateLimitedException {
    public ReauthenticationRateLimitedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TooManyConcurrentLoginsException extends RateLimitedException {
    public TooManyConcurrentLoginsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TooManyConcurrentRequestsException extends RateLimitedException {
    public TooManyConcurrentRequestsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TooManyLoginAttemptsException extends RateLimitedException {
    public TooManyLoginAttemptsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TooManyRequestsException extends RateLimitedException {
    public TooManyRequestsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TooManySharesException extends RateLimitedException {
    public TooManySharesException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class ServiceUnavailableException extends ApiErrorException {
    public ServiceUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AgentUnavailableException extends ServiceUnavailableException {
    public AgentUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AutomationsUnavailableException extends ServiceUnavailableException {
    public AutomationsUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class MigrationInProgressException extends ServiceUnavailableException {
    public MigrationInProgressException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SiteDisabledException extends ServiceUnavailableException {
    public SiteDisabledException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UploadsUnavailableException extends ServiceUnavailableException {
    public UploadsUnavailableException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SiteConfigurationException extends ApiErrorException {
    public SiteConfigurationException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AccountAlreadyExistsException extends SiteConfigurationException {
    public AccountAlreadyExistsException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class AccountOverdueException extends SiteConfigurationException {
    public AccountOverdueException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class NoAccountForSiteException extends SiteConfigurationException {
    public NoAccountForSiteException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class SiteWasRemovedException extends SiteConfigurationException {
    public SiteWasRemovedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TrialExpiredException extends SiteConfigurationException {
    public TrialExpiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class TrialLockedException extends SiteConfigurationException {
    public TrialLockedException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

  public static class UserRequestsEnabledRequiredException extends SiteConfigurationException {
    public UserRequestsEnabledRequiredException(String message, ResponseError responseError, List<Header> headers) {
      super(message, responseError, headers);
    }
  }

}
