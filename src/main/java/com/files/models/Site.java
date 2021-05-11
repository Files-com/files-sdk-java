package com.files.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.files.FilesClient;
import com.files.FilesConfig;
import com.files.net.HttpMethods.RequestMethods;
import com.files.util.ModelUtils;
import com.files.util.FilesInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class Site {
  private HashMap<String, Object> options;
  private ObjectMapper objectMapper = new ObjectMapper();

  public Site() {
    this(null, null);
  }

  public Site(HashMap<String, Object> parameters) {
    this(parameters, null);
  }

  public Site(HashMap<String, Object> parameters, HashMap<String, Object> options) {
    this.options = options;
    try{
      ObjectReader objectReader = objectMapper.readerForUpdating(this);
      objectReader.readValue(objectMapper.writeValueAsString(parameters));
    } catch (JsonProcessingException e){
      // TODO: error generation on constructor
    }
  }

  /**
  * Site name
  */
  @Getter
  @JsonProperty("name")
  private String name;

  /**
  * Is SMS two factor authentication allowed?
  */
  @Getter
  @JsonProperty("allowed_2fa_method_sms")
  private Boolean allowed2faMethodSms;

  /**
  * Is TOTP two factor authentication allowed?
  */
  @Getter
  @JsonProperty("allowed_2fa_method_totp")
  private Boolean allowed2faMethodTotp;

  /**
  * Is U2F two factor authentication allowed?
  */
  @Getter
  @JsonProperty("allowed_2fa_method_u2f")
  private Boolean allowed2faMethodU2f;

  /**
  * Is yubikey two factor authentication allowed?
  */
  @Getter
  @JsonProperty("allowed_2fa_method_yubi")
  private Boolean allowed2faMethodYubi;

  /**
  * User ID for the main site administrator
  */
  @Getter
  @JsonProperty("admin_user_id")
  private Long adminUserId;

  /**
  * Are manual Bundle names allowed?
  */
  @Getter
  @JsonProperty("allow_bundle_names")
  private Boolean allowBundleNames;

  /**
  * Comma seperated list of allowed Country codes
  */
  @Getter
  @JsonProperty("allowed_countries")
  private String allowedCountries;

  /**
  * List of allowed IP addresses
  */
  @Getter
  @JsonProperty("allowed_ips")
  private String allowedIps;

  /**
  * If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
  */
  @Getter
  @JsonProperty("ask_about_overwrites")
  private Boolean askAboutOverwrites;

  /**
  * Site-wide Bundle expiration in days
  */
  @Getter
  @JsonProperty("bundle_expiration")
  private Long bundleExpiration;

  /**
  * Do Bundles require password protection?
  */
  @Getter
  @JsonProperty("bundle_password_required")
  private Boolean bundlePasswordRequired;

  /**
  * Do Bundles require recipients for sharing?
  */
  @Getter
  @JsonProperty("bundle_require_share_recipient")
  private Boolean bundleRequireShareRecipient;

  /**
  * Page link and button color
  */
  @Getter
  @JsonProperty("color2_left")
  private String color2Left;

  /**
  * Top bar link color
  */
  @Getter
  @JsonProperty("color2_link")
  private String color2Link;

  /**
  * Page link and button color
  */
  @Getter
  @JsonProperty("color2_text")
  private String color2Text;

  /**
  * Top bar background color
  */
  @Getter
  @JsonProperty("color2_top")
  private String color2Top;

  /**
  * Top bar text color
  */
  @Getter
  @JsonProperty("color2_top_text")
  private String color2TopText;

  /**
  * Site main contact name
  */
  @Getter
  @JsonProperty("contact_name")
  private String contactName;

  /**
  * Time this site was created
  */
  @Getter
  @JsonProperty("created_at")
  private Date createdAt;

  /**
  * Preferred currency
  */
  @Getter
  @JsonProperty("currency")
  private String currency;

  /**
  * Is this site using a custom namespace for users?
  */
  @Getter
  @JsonProperty("custom_namespace")
  private Boolean customNamespace;

  /**
  * Number of days to keep deleted files
  */
  @Getter
  @JsonProperty("days_to_retain_backups")
  private Long daysToRetainBackups;

  /**
  * Site default time zone
  */
  @Getter
  @JsonProperty("default_time_zone")
  private String defaultTimeZone;

  /**
  * Is the desktop app enabled?
  */
  @Getter
  @JsonProperty("desktop_app")
  private Boolean desktopApp;

  /**
  * Is desktop app session IP pinning enabled?
  */
  @Getter
  @JsonProperty("desktop_app_session_ip_pinning")
  private Boolean desktopAppSessionIpPinning;

  /**
  * Desktop app session lifetime (in hours)
  */
  @Getter
  @JsonProperty("desktop_app_session_lifetime")
  private Long desktopAppSessionLifetime;

  /**
  * Comma seperated list of disallowed Country codes
  */
  @Getter
  @JsonProperty("disallowed_countries")
  private String disallowedCountries;

  /**
  * Are notifications disabled?
  */
  @Getter
  @JsonProperty("disable_notifications")
  private Boolean disableNotifications;

  /**
  * Is password reset disabled?
  */
  @Getter
  @JsonProperty("disable_password_reset")
  private Boolean disablePasswordReset;

  /**
  * Custom domain
  */
  @Getter
  @JsonProperty("domain")
  private String domain;

  /**
  * Main email for this site
  */
  @Getter
  @JsonProperty("email")
  private String email;

  /**
  * Reply-to email for this site
  */
  @Getter
  @JsonProperty("reply_to_email")
  private String replyToEmail;

  /**
  * If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
  */
  @Getter
  @JsonProperty("non_sso_groups_allowed")
  private Boolean nonSsoGroupsAllowed;

  /**
  * If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
  */
  @Getter
  @JsonProperty("non_sso_users_allowed")
  private Boolean nonSsoUsersAllowed;

  /**
  * If true, permissions for this site must be bound to a group (not a user). Otherwise, permissions must be bound to a user.
  */
  @Getter
  @JsonProperty("folder_permissions_groups_only")
  private Boolean folderPermissionsGroupsOnly;

  /**
  * Is there a signed HIPAA BAA between Files.com and this site?
  */
  @Getter
  @JsonProperty("hipaa")
  private Boolean hipaa;

  /**
  * Branded icon 128x128
  */
  @Getter
  @JsonProperty("icon128")
  private Map<String, String> icon128;

  /**
  * Branded icon 16x16
  */
  @Getter
  @JsonProperty("icon16")
  private Map<String, String> icon16;

  /**
  * Branded icon 32x32
  */
  @Getter
  @JsonProperty("icon32")
  private Map<String, String> icon32;

  /**
  * Branded icon 48x48
  */
  @Getter
  @JsonProperty("icon48")
  private Map<String, String> icon48;

  /**
  * Can files be modified?
  */
  @Getter
  @JsonProperty("immutable_files_set_at")
  private Date immutableFilesSetAt;

  /**
  * Include password in emails to new users?
  */
  @Getter
  @JsonProperty("include_password_in_welcome_email")
  private Boolean includePasswordInWelcomeEmail;

  /**
  * Site default language
  */
  @Getter
  @JsonProperty("language")
  private String language;

  /**
  * Base DN for looking up users in LDAP server
  */
  @Getter
  @JsonProperty("ldap_base_dn")
  private String ldapBaseDn;

  /**
  * Domain name that will be appended to usernames
  */
  @Getter
  @JsonProperty("ldap_domain")
  private String ldapDomain;

  /**
  * Main LDAP setting: is LDAP enabled?
  */
  @Getter
  @JsonProperty("ldap_enabled")
  private Boolean ldapEnabled;

  /**
  * Should we sync groups from LDAP server?
  */
  @Getter
  @JsonProperty("ldap_group_action")
  private String ldapGroupAction;

  /**
  * Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
  */
  @Getter
  @JsonProperty("ldap_group_exclusion")
  private String ldapGroupExclusion;

  /**
  * Comma or newline separated list of group names (with optional wildcards) to include when syncing.
  */
  @Getter
  @JsonProperty("ldap_group_inclusion")
  private String ldapGroupInclusion;

  /**
  * LDAP host
  */
  @Getter
  @JsonProperty("ldap_host")
  private String ldapHost;

  /**
  * LDAP backup host
  */
  @Getter
  @JsonProperty("ldap_host_2")
  private String ldapHost2;

  /**
  * LDAP backup host
  */
  @Getter
  @JsonProperty("ldap_host_3")
  private String ldapHost3;

  /**
  * LDAP port
  */
  @Getter
  @JsonProperty("ldap_port")
  private Long ldapPort;

  /**
  * Use secure LDAP?
  */
  @Getter
  @JsonProperty("ldap_secure")
  private Boolean ldapSecure;

  /**
  * LDAP type
  */
  @Getter
  @JsonProperty("ldap_type")
  private String ldapType;

  /**
  * Should we sync users from LDAP server?
  */
  @Getter
  @JsonProperty("ldap_user_action")
  private String ldapUserAction;

  /**
  * Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
  */
  @Getter
  @JsonProperty("ldap_user_include_groups")
  private String ldapUserIncludeGroups;

  /**
  * Username for signing in to LDAP server.
  */
  @Getter
  @JsonProperty("ldap_username")
  private String ldapUsername;

  /**
  * LDAP username field
  */
  @Getter
  @JsonProperty("ldap_username_field")
  private String ldapUsernameField;

  /**
  * Login help text
  */
  @Getter
  @JsonProperty("login_help_text")
  private String loginHelpText;

  /**
  * Branded logo
  */
  @Getter
  @JsonProperty("logo")
  private Map<String, String> logo;

  /**
  * Number of prior passwords to disallow
  */
  @Getter
  @JsonProperty("max_prior_passwords")
  private Long maxPriorPasswords;

  /**
  * Next billing amount
  */
  @Getter
  @JsonProperty("next_billing_amount")
  private Double nextBillingAmount;

  /**
  * Next billing date
  */
  @Getter
  @JsonProperty("next_billing_date")
  private String nextBillingDate;

  /**
  * Allow users to use Office for the web?
  */
  @Getter
  @JsonProperty("office_integration_available")
  private Boolean officeIntegrationAvailable;

  /**
  * Link to scheduling a meeting with our Sales team
  */
  @Getter
  @JsonProperty("oncehub_link")
  private String oncehubLink;

  /**
  * Use servers in the USA only?
  */
  @Getter
  @JsonProperty("opt_out_global")
  private Boolean optOutGlobal;

  /**
  * Last time the site was notified about an overage
  */
  @Getter
  @JsonProperty("overage_notified_at")
  private Date overageNotifiedAt;

  /**
  * Notify site email of overages?
  */
  @Getter
  @JsonProperty("overage_notify")
  private Boolean overageNotify;

  /**
  * Is this site's billing overdue?
  */
  @Getter
  @JsonProperty("overdue")
  private Boolean overdue;

  /**
  * Shortest password length for users
  */
  @Getter
  @JsonProperty("password_min_length")
  private Long passwordMinLength;

  /**
  * Require a letter in passwords?
  */
  @Getter
  @JsonProperty("password_require_letter")
  private Boolean passwordRequireLetter;

  /**
  * Require lower and upper case letters in passwords?
  */
  @Getter
  @JsonProperty("password_require_mixed")
  private Boolean passwordRequireMixed;

  /**
  * Require a number in passwords?
  */
  @Getter
  @JsonProperty("password_require_number")
  private Boolean passwordRequireNumber;

  /**
  * Require special characters in password?
  */
  @Getter
  @JsonProperty("password_require_special")
  private Boolean passwordRequireSpecial;

  /**
  * Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
  */
  @Getter
  @JsonProperty("password_require_unbreached")
  private Boolean passwordRequireUnbreached;

  /**
  * Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
  */
  @Getter
  @JsonProperty("password_requirements_apply_to_bundles")
  private Boolean passwordRequirementsApplyToBundles;

  /**
  * Number of days password is valid
  */
  @Getter
  @JsonProperty("password_validity_days")
  private Long passwordValidityDays;

  /**
  * Site phone number
  */
  @Getter
  @JsonProperty("phone")
  private String phone;

  /**
  * Require two-factor authentication for all users?
  */
  @Getter
  @JsonProperty("require_2fa")
  private Boolean require2fa;

  /**
  * If set, requirement for two-factor authentication has been scheduled to end on this date-time.
  */
  @Getter
  @JsonProperty("require_2fa_stop_time")
  private Date require2faStopTime;

  /**
  * What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
  */
  @Getter
  @JsonProperty("require_2fa_user_type")
  private String require2faUserType;

  /**
  * Current session
  */
  @Getter
  @JsonProperty("session")
  private Map<String, String> session;

  /**
  * Are sessions locked to the same IP? (i.e. do users need to log in again if they change IPs?)
  */
  @Getter
  @JsonProperty("session_pinned_by_ip")
  private Boolean sessionPinnedByIp;

  /**
  * Use user FTP roots also for SFTP?
  */
  @Getter
  @JsonProperty("sftp_user_root_enabled")
  private Boolean sftpUserRootEnabled;

  /**
  * Allow bundle creation
  */
  @Getter
  @JsonProperty("sharing_enabled")
  private Boolean sharingEnabled;

  /**
  * Show request access link for users without access?  Currently unused.
  */
  @Getter
  @JsonProperty("show_request_access_link")
  private Boolean showRequestAccessLink;

  /**
  * Custom site footer text
  */
  @Getter
  @JsonProperty("site_footer")
  private String siteFooter;

  /**
  * Custom site header text
  */
  @Getter
  @JsonProperty("site_header")
  private String siteHeader;

  /**
  * SMTP server hostname or IP
  */
  @Getter
  @JsonProperty("smtp_address")
  private String smtpAddress;

  /**
  * SMTP server authentication type
  */
  @Getter
  @JsonProperty("smtp_authentication")
  private String smtpAuthentication;

  /**
  * From address to use when mailing through custom SMTP
  */
  @Getter
  @JsonProperty("smtp_from")
  private String smtpFrom;

  /**
  * SMTP server port
  */
  @Getter
  @JsonProperty("smtp_port")
  private Long smtpPort;

  /**
  * SMTP server username
  */
  @Getter
  @JsonProperty("smtp_username")
  private String smtpUsername;

  /**
  * Session expiry in hours
  */
  @Getter
  @JsonProperty("session_expiry")
  private Double sessionExpiry;

  /**
  * Is SSL required?  Disabling this is insecure.
  */
  @Getter
  @JsonProperty("ssl_required")
  private Boolean sslRequired;

  /**
  * Site subdomain
  */
  @Getter
  @JsonProperty("subdomain")
  private String subdomain;

  /**
  * If switching plans, when does the new plan take effect?
  */
  @Getter
  @JsonProperty("switch_to_plan_date")
  private Date switchToPlanDate;

  /**
  * Is TLS disabled(site setting)?
  */
  @Getter
  @JsonProperty("tls_disabled")
  private Boolean tlsDisabled;

  /**
  * Number of days left in trial
  */
  @Getter
  @JsonProperty("trial_days_left")
  private Long trialDaysLeft;

  /**
  * When does this Site trial expire?
  */
  @Getter
  @JsonProperty("trial_until")
  private Date trialUntil;

  /**
  * Last time this Site was updated
  */
  @Getter
  @JsonProperty("updated_at")
  private Date updatedAt;

  /**
  * Allow uploaders to set `provided_modified_at` for uploaded files?
  */
  @Getter
  @JsonProperty("use_provided_modified_at")
  private Boolean useProvidedModifiedAt;

  /**
  * User of current session
  */
  @Getter
  @JsonProperty("user")
  private Map<String, String> user;

  /**
  * Will users be locked out after incorrect login attempts?
  */
  @Getter
  @JsonProperty("user_lockout")
  private Boolean userLockout;

  /**
  * How many hours to lock user out for failed password?
  */
  @Getter
  @JsonProperty("user_lockout_lock_period")
  private Long userLockoutLockPeriod;

  /**
  * Number of login tries within `user_lockout_within` hours before users are locked out
  */
  @Getter
  @JsonProperty("user_lockout_tries")
  private Long userLockoutTries;

  /**
  * Number of hours for user lockout window
  */
  @Getter
  @JsonProperty("user_lockout_within")
  private Long userLockoutWithin;

  /**
  * Enable User Requests feature
  */
  @Getter
  @JsonProperty("user_requests_enabled")
  private Boolean userRequestsEnabled;

  /**
  * Custom text send in user welcome email
  */
  @Getter
  @JsonProperty("welcome_custom_text")
  private String welcomeCustomText;

  /**
  * Include this email in welcome emails if enabled
  */
  @Getter
  @JsonProperty("welcome_email_cc")
  private String welcomeEmailCc;

  /**
  * Will the welcome email be sent to new users?
  */
  @Getter
  @JsonProperty("welcome_email_enabled")
  private Boolean welcomeEmailEnabled;

  /**
  * Does the welcome screen appear?
  */
  @Getter
  @JsonProperty("welcome_screen")
  private String welcomeScreen;

  /**
  * Does FTP user Windows emulation mode?
  */
  @Getter
  @JsonProperty("windows_mode_ftp")
  private Boolean windowsModeFtp;

  /**
  * If greater than zero, users will unable to login if they do not show activity within this number of days.
  */
  @Getter
  @JsonProperty("disable_users_from_inactivity_period_days")
  private Long disableUsersFromInactivityPeriodDays;



  /**
  */
  public static Site get() throws IOException{
    return get(null,null);
  }
  public static Site get( HashMap<String, Object> parameters) throws IOException {
    return get(parameters, null);
  }


  public static Site get( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    String url = String.format("%s%s/site", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  */
  public static Site getUsage() throws IOException{
    return getUsage(null,null);
  }
  public static Site getUsage( HashMap<String, Object> parameters) throws IOException {
    return getUsage(parameters, null);
  }


  public static Site getUsage( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    String url = String.format("%s%s/site/usage", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return FilesClient.requestItem(url, RequestMethods.GET, typeReference, parameters, options);
  }


  /**
  * Parameters:
  *   name - string - Site name
  *   subdomain - string - Site subdomain
  *   domain - string - Custom domain
  *   email - string - Main email for this site
  *   reply_to_email - string - Reply-to email for this site
  *   allow_bundle_names - boolean - Are manual Bundle names allowed?
  *   bundle_expiration - int64 - Site-wide Bundle expiration in days
  *   overage_notify - boolean - Notify site email of overages?
  *   welcome_email_enabled - boolean - Will the welcome email be sent to new users?
  *   ask_about_overwrites - boolean - If false, rename conflicting files instead of asking for overwrite confirmation.  Only applies to web interface.
  *   show_request_access_link - boolean - Show request access link for users without access?  Currently unused.
  *   welcome_email_cc - string - Include this email in welcome emails if enabled
  *   welcome_custom_text - string - Custom text send in user welcome email
  *   language - string - Site default language
  *   windows_mode_ftp - boolean - Does FTP user Windows emulation mode?
  *   default_time_zone - string - Site default time zone
  *   desktop_app - boolean - Is the desktop app enabled?
  *   desktop_app_session_ip_pinning - boolean - Is desktop app session IP pinning enabled?
  *   desktop_app_session_lifetime - int64 - Desktop app session lifetime (in hours)
  *   folder_permissions_groups_only - boolean - If true, permissions for this site must be bound to a group (not a user). Otherwise, permissions must be bound to a user.
  *   welcome_screen - string - Does the welcome screen appear?
  *   office_integration_available - boolean - Allow users to use Office for the web?
  *   session_expiry - double - Session expiry in hours
  *   ssl_required - boolean - Is SSL required?  Disabling this is insecure.
  *   tls_disabled - boolean - Is TLS disabled(site setting)?
  *   user_lockout - boolean - Will users be locked out after incorrect login attempts?
  *   user_lockout_tries - int64 - Number of login tries within `user_lockout_within` hours before users are locked out
  *   user_lockout_within - int64 - Number of hours for user lockout window
  *   user_lockout_lock_period - int64 - How many hours to lock user out for failed password?
  *   include_password_in_welcome_email - boolean - Include password in emails to new users?
  *   allowed_countries - string - Comma seperated list of allowed Country codes
  *   allowed_ips - string - List of allowed IP addresses
  *   disallowed_countries - string - Comma seperated list of disallowed Country codes
  *   days_to_retain_backups - int64 - Number of days to keep deleted files
  *   max_prior_passwords - int64 - Number of prior passwords to disallow
  *   password_validity_days - int64 - Number of days password is valid
  *   password_min_length - int64 - Shortest password length for users
  *   password_require_letter - boolean - Require a letter in passwords?
  *   password_require_mixed - boolean - Require lower and upper case letters in passwords?
  *   password_require_special - boolean - Require special characters in password?
  *   password_require_number - boolean - Require a number in passwords?
  *   password_require_unbreached - boolean - Require passwords that have not been previously breached? (see https://haveibeenpwned.com/)
  *   sftp_user_root_enabled - boolean - Use user FTP roots also for SFTP?
  *   disable_password_reset - boolean - Is password reset disabled?
  *   immutable_files - boolean - Are files protected from modification?
  *   session_pinned_by_ip - boolean - Are sessions locked to the same IP? (i.e. do users need to log in again if they change IPs?)
  *   bundle_password_required - boolean - Do Bundles require password protection?
  *   bundle_require_share_recipient - boolean - Do Bundles require recipients for sharing?
  *   password_requirements_apply_to_bundles - boolean - Require bundles' passwords, and passwords for other items (inboxes, public shares, etc.) to conform to the same requirements as users' passwords?
  *   opt_out_global - boolean - Use servers in the USA only?
  *   use_provided_modified_at - boolean - Allow uploaders to set `provided_modified_at` for uploaded files?
  *   custom_namespace - boolean - Is this site using a custom namespace for users?
  *   disable_users_from_inactivity_period_days - int64 - If greater than zero, users will unable to login if they do not show activity within this number of days.
  *   non_sso_groups_allowed - boolean - If true, groups can be manually created / modified / deleted by Site Admins. Otherwise, groups can only be managed via your SSO provider.
  *   non_sso_users_allowed - boolean - If true, users can be manually created / modified / deleted by Site Admins. Otherwise, users can only be managed via your SSO provider.
  *   sharing_enabled - boolean - Allow bundle creation
  *   user_requests_enabled - boolean - Enable User Requests feature
  *   allowed_2fa_method_sms - boolean - Is SMS two factor authentication allowed?
  *   allowed_2fa_method_u2f - boolean - Is U2F two factor authentication allowed?
  *   allowed_2fa_method_totp - boolean - Is TOTP two factor authentication allowed?
  *   allowed_2fa_method_yubi - boolean - Is yubikey two factor authentication allowed?
  *   require_2fa - boolean - Require two-factor authentication for all users?
  *   require_2fa_user_type - string - What type of user is required to use two-factor authentication (when require_2fa is set to `true` for this site)?
  *   color2_top - string - Top bar background color
  *   color2_left - string - Page link and button color
  *   color2_link - string - Top bar link color
  *   color2_text - string - Page link and button color
  *   color2_top_text - string - Top bar text color
  *   site_header - string - Custom site header text
  *   site_footer - string - Custom site footer text
  *   login_help_text - string - Login help text
  *   smtp_address - string - SMTP server hostname or IP
  *   smtp_authentication - string - SMTP server authentication type
  *   smtp_from - string - From address to use when mailing through custom SMTP
  *   smtp_username - string - SMTP server username
  *   smtp_port - int64 - SMTP server port
  *   ldap_enabled - boolean - Main LDAP setting: is LDAP enabled?
  *   ldap_type - string - LDAP type
  *   ldap_host - string - LDAP host
  *   ldap_host_2 - string - LDAP backup host
  *   ldap_host_3 - string - LDAP backup host
  *   ldap_port - int64 - LDAP port
  *   ldap_secure - boolean - Use secure LDAP?
  *   ldap_username - string - Username for signing in to LDAP server.
  *   ldap_username_field - string - LDAP username field
  *   ldap_domain - string - Domain name that will be appended to usernames
  *   ldap_user_action - string - Should we sync users from LDAP server?
  *   ldap_group_action - string - Should we sync groups from LDAP server?
  *   ldap_user_include_groups - string - Comma or newline separated list of group names (with optional wildcards) - if provided, only users in these groups will be added or synced.
  *   ldap_group_exclusion - string - Comma or newline separated list of group names (with optional wildcards) to exclude when syncing.
  *   ldap_group_inclusion - string - Comma or newline separated list of group names (with optional wildcards) to include when syncing.
  *   ldap_base_dn - string - Base DN for looking up users in LDAP server
  *   icon16_file - file
  *   icon16_delete - boolean - If true, will delete the file stored in icon16
  *   icon32_file - file
  *   icon32_delete - boolean - If true, will delete the file stored in icon32
  *   icon48_file - file
  *   icon48_delete - boolean - If true, will delete the file stored in icon48
  *   icon128_file - file
  *   icon128_delete - boolean - If true, will delete the file stored in icon128
  *   logo_file - file
  *   logo_delete - boolean - If true, will delete the file stored in logo
  *   disable_2fa_with_delay - boolean - If set to true, we will begin the process of disabling 2FA on this site.
  *   ldap_password_change - string - New LDAP password.
  *   ldap_password_change_confirmation - string - Confirm new LDAP password.
  *   smtp_password - string - Password for SMTP server.
  */
  public static Site update() throws IOException{
    return update(null,null);
  }
  public static Site update( HashMap<String, Object> parameters) throws IOException {
    return update(parameters, null);
  }


  public static Site update( HashMap<String, Object> parameters, HashMap<String, Object> options) throws IOException {
    parameters = parameters != null ? parameters : new HashMap<String, Object>();
    options = options != null ? options : new HashMap<String, Object>();

    if (parameters.containsKey("name") && !(parameters.get("name") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: name must be of type String parameters[\"name\"]");
    }

    if (parameters.containsKey("subdomain") && !(parameters.get("subdomain") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: subdomain must be of type String parameters[\"subdomain\"]");
    }

    if (parameters.containsKey("domain") && !(parameters.get("domain") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: domain must be of type String parameters[\"domain\"]");
    }

    if (parameters.containsKey("email") && !(parameters.get("email") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: email must be of type String parameters[\"email\"]");
    }

    if (parameters.containsKey("reply_to_email") && !(parameters.get("reply_to_email") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: reply_to_email must be of type String parameters[\"reply_to_email\"]");
    }

    if (parameters.containsKey("allow_bundle_names") && !(parameters.get("allow_bundle_names") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: allow_bundle_names must be of type Boolean parameters[\"allow_bundle_names\"]");
    }

    if (parameters.containsKey("bundle_expiration") && !(parameters.get("bundle_expiration") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: bundle_expiration must be of type Long parameters[\"bundle_expiration\"]");
    }

    if (parameters.containsKey("overage_notify") && !(parameters.get("overage_notify") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: overage_notify must be of type Boolean parameters[\"overage_notify\"]");
    }

    if (parameters.containsKey("welcome_email_enabled") && !(parameters.get("welcome_email_enabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: welcome_email_enabled must be of type Boolean parameters[\"welcome_email_enabled\"]");
    }

    if (parameters.containsKey("ask_about_overwrites") && !(parameters.get("ask_about_overwrites") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: ask_about_overwrites must be of type Boolean parameters[\"ask_about_overwrites\"]");
    }

    if (parameters.containsKey("show_request_access_link") && !(parameters.get("show_request_access_link") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: show_request_access_link must be of type Boolean parameters[\"show_request_access_link\"]");
    }

    if (parameters.containsKey("welcome_email_cc") && !(parameters.get("welcome_email_cc") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: welcome_email_cc must be of type String parameters[\"welcome_email_cc\"]");
    }

    if (parameters.containsKey("welcome_custom_text") && !(parameters.get("welcome_custom_text") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: welcome_custom_text must be of type String parameters[\"welcome_custom_text\"]");
    }

    if (parameters.containsKey("language") && !(parameters.get("language") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: language must be of type String parameters[\"language\"]");
    }

    if (parameters.containsKey("windows_mode_ftp") && !(parameters.get("windows_mode_ftp") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: windows_mode_ftp must be of type Boolean parameters[\"windows_mode_ftp\"]");
    }

    if (parameters.containsKey("default_time_zone") && !(parameters.get("default_time_zone") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: default_time_zone must be of type String parameters[\"default_time_zone\"]");
    }

    if (parameters.containsKey("desktop_app") && !(parameters.get("desktop_app") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: desktop_app must be of type Boolean parameters[\"desktop_app\"]");
    }

    if (parameters.containsKey("desktop_app_session_ip_pinning") && !(parameters.get("desktop_app_session_ip_pinning") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: desktop_app_session_ip_pinning must be of type Boolean parameters[\"desktop_app_session_ip_pinning\"]");
    }

    if (parameters.containsKey("desktop_app_session_lifetime") && !(parameters.get("desktop_app_session_lifetime") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: desktop_app_session_lifetime must be of type Long parameters[\"desktop_app_session_lifetime\"]");
    }

    if (parameters.containsKey("folder_permissions_groups_only") && !(parameters.get("folder_permissions_groups_only") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: folder_permissions_groups_only must be of type Boolean parameters[\"folder_permissions_groups_only\"]");
    }

    if (parameters.containsKey("welcome_screen") && !(parameters.get("welcome_screen") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: welcome_screen must be of type String parameters[\"welcome_screen\"]");
    }

    if (parameters.containsKey("office_integration_available") && !(parameters.get("office_integration_available") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: office_integration_available must be of type Boolean parameters[\"office_integration_available\"]");
    }

    if (parameters.containsKey("session_expiry") && !(parameters.get("session_expiry") instanceof Double )) {
      throw new IllegalArgumentException("Bad parameter: session_expiry must be of type Double parameters[\"session_expiry\"]");
    }

    if (parameters.containsKey("ssl_required") && !(parameters.get("ssl_required") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: ssl_required must be of type Boolean parameters[\"ssl_required\"]");
    }

    if (parameters.containsKey("tls_disabled") && !(parameters.get("tls_disabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: tls_disabled must be of type Boolean parameters[\"tls_disabled\"]");
    }

    if (parameters.containsKey("user_lockout") && !(parameters.get("user_lockout") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: user_lockout must be of type Boolean parameters[\"user_lockout\"]");
    }

    if (parameters.containsKey("user_lockout_tries") && !(parameters.get("user_lockout_tries") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_lockout_tries must be of type Long parameters[\"user_lockout_tries\"]");
    }

    if (parameters.containsKey("user_lockout_within") && !(parameters.get("user_lockout_within") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_lockout_within must be of type Long parameters[\"user_lockout_within\"]");
    }

    if (parameters.containsKey("user_lockout_lock_period") && !(parameters.get("user_lockout_lock_period") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: user_lockout_lock_period must be of type Long parameters[\"user_lockout_lock_period\"]");
    }

    if (parameters.containsKey("include_password_in_welcome_email") && !(parameters.get("include_password_in_welcome_email") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: include_password_in_welcome_email must be of type Boolean parameters[\"include_password_in_welcome_email\"]");
    }

    if (parameters.containsKey("allowed_countries") && !(parameters.get("allowed_countries") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: allowed_countries must be of type String parameters[\"allowed_countries\"]");
    }

    if (parameters.containsKey("allowed_ips") && !(parameters.get("allowed_ips") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: allowed_ips must be of type String parameters[\"allowed_ips\"]");
    }

    if (parameters.containsKey("disallowed_countries") && !(parameters.get("disallowed_countries") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: disallowed_countries must be of type String parameters[\"disallowed_countries\"]");
    }

    if (parameters.containsKey("days_to_retain_backups") && !(parameters.get("days_to_retain_backups") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: days_to_retain_backups must be of type Long parameters[\"days_to_retain_backups\"]");
    }

    if (parameters.containsKey("max_prior_passwords") && !(parameters.get("max_prior_passwords") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: max_prior_passwords must be of type Long parameters[\"max_prior_passwords\"]");
    }

    if (parameters.containsKey("password_validity_days") && !(parameters.get("password_validity_days") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: password_validity_days must be of type Long parameters[\"password_validity_days\"]");
    }

    if (parameters.containsKey("password_min_length") && !(parameters.get("password_min_length") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: password_min_length must be of type Long parameters[\"password_min_length\"]");
    }

    if (parameters.containsKey("password_require_letter") && !(parameters.get("password_require_letter") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: password_require_letter must be of type Boolean parameters[\"password_require_letter\"]");
    }

    if (parameters.containsKey("password_require_mixed") && !(parameters.get("password_require_mixed") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: password_require_mixed must be of type Boolean parameters[\"password_require_mixed\"]");
    }

    if (parameters.containsKey("password_require_special") && !(parameters.get("password_require_special") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: password_require_special must be of type Boolean parameters[\"password_require_special\"]");
    }

    if (parameters.containsKey("password_require_number") && !(parameters.get("password_require_number") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: password_require_number must be of type Boolean parameters[\"password_require_number\"]");
    }

    if (parameters.containsKey("password_require_unbreached") && !(parameters.get("password_require_unbreached") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: password_require_unbreached must be of type Boolean parameters[\"password_require_unbreached\"]");
    }

    if (parameters.containsKey("sftp_user_root_enabled") && !(parameters.get("sftp_user_root_enabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: sftp_user_root_enabled must be of type Boolean parameters[\"sftp_user_root_enabled\"]");
    }

    if (parameters.containsKey("disable_password_reset") && !(parameters.get("disable_password_reset") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: disable_password_reset must be of type Boolean parameters[\"disable_password_reset\"]");
    }

    if (parameters.containsKey("immutable_files") && !(parameters.get("immutable_files") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: immutable_files must be of type Boolean parameters[\"immutable_files\"]");
    }

    if (parameters.containsKey("session_pinned_by_ip") && !(parameters.get("session_pinned_by_ip") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: session_pinned_by_ip must be of type Boolean parameters[\"session_pinned_by_ip\"]");
    }

    if (parameters.containsKey("bundle_password_required") && !(parameters.get("bundle_password_required") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: bundle_password_required must be of type Boolean parameters[\"bundle_password_required\"]");
    }

    if (parameters.containsKey("bundle_require_share_recipient") && !(parameters.get("bundle_require_share_recipient") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: bundle_require_share_recipient must be of type Boolean parameters[\"bundle_require_share_recipient\"]");
    }

    if (parameters.containsKey("password_requirements_apply_to_bundles") && !(parameters.get("password_requirements_apply_to_bundles") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: password_requirements_apply_to_bundles must be of type Boolean parameters[\"password_requirements_apply_to_bundles\"]");
    }

    if (parameters.containsKey("opt_out_global") && !(parameters.get("opt_out_global") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: opt_out_global must be of type Boolean parameters[\"opt_out_global\"]");
    }

    if (parameters.containsKey("use_provided_modified_at") && !(parameters.get("use_provided_modified_at") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: use_provided_modified_at must be of type Boolean parameters[\"use_provided_modified_at\"]");
    }

    if (parameters.containsKey("custom_namespace") && !(parameters.get("custom_namespace") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: custom_namespace must be of type Boolean parameters[\"custom_namespace\"]");
    }

    if (parameters.containsKey("disable_users_from_inactivity_period_days") && !(parameters.get("disable_users_from_inactivity_period_days") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: disable_users_from_inactivity_period_days must be of type Long parameters[\"disable_users_from_inactivity_period_days\"]");
    }

    if (parameters.containsKey("non_sso_groups_allowed") && !(parameters.get("non_sso_groups_allowed") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: non_sso_groups_allowed must be of type Boolean parameters[\"non_sso_groups_allowed\"]");
    }

    if (parameters.containsKey("non_sso_users_allowed") && !(parameters.get("non_sso_users_allowed") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: non_sso_users_allowed must be of type Boolean parameters[\"non_sso_users_allowed\"]");
    }

    if (parameters.containsKey("sharing_enabled") && !(parameters.get("sharing_enabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: sharing_enabled must be of type Boolean parameters[\"sharing_enabled\"]");
    }

    if (parameters.containsKey("user_requests_enabled") && !(parameters.get("user_requests_enabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: user_requests_enabled must be of type Boolean parameters[\"user_requests_enabled\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_sms") && !(parameters.get("allowed_2fa_method_sms") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_sms must be of type Boolean parameters[\"allowed_2fa_method_sms\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_u2f") && !(parameters.get("allowed_2fa_method_u2f") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_u2f must be of type Boolean parameters[\"allowed_2fa_method_u2f\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_totp") && !(parameters.get("allowed_2fa_method_totp") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_totp must be of type Boolean parameters[\"allowed_2fa_method_totp\"]");
    }

    if (parameters.containsKey("allowed_2fa_method_yubi") && !(parameters.get("allowed_2fa_method_yubi") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: allowed_2fa_method_yubi must be of type Boolean parameters[\"allowed_2fa_method_yubi\"]");
    }

    if (parameters.containsKey("require_2fa") && !(parameters.get("require_2fa") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: require_2fa must be of type Boolean parameters[\"require_2fa\"]");
    }

    if (parameters.containsKey("require_2fa_user_type") && !(parameters.get("require_2fa_user_type") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: require_2fa_user_type must be of type String parameters[\"require_2fa_user_type\"]");
    }

    if (parameters.containsKey("color2_top") && !(parameters.get("color2_top") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: color2_top must be of type String parameters[\"color2_top\"]");
    }

    if (parameters.containsKey("color2_left") && !(parameters.get("color2_left") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: color2_left must be of type String parameters[\"color2_left\"]");
    }

    if (parameters.containsKey("color2_link") && !(parameters.get("color2_link") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: color2_link must be of type String parameters[\"color2_link\"]");
    }

    if (parameters.containsKey("color2_text") && !(parameters.get("color2_text") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: color2_text must be of type String parameters[\"color2_text\"]");
    }

    if (parameters.containsKey("color2_top_text") && !(parameters.get("color2_top_text") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: color2_top_text must be of type String parameters[\"color2_top_text\"]");
    }

    if (parameters.containsKey("site_header") && !(parameters.get("site_header") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: site_header must be of type String parameters[\"site_header\"]");
    }

    if (parameters.containsKey("site_footer") && !(parameters.get("site_footer") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: site_footer must be of type String parameters[\"site_footer\"]");
    }

    if (parameters.containsKey("login_help_text") && !(parameters.get("login_help_text") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: login_help_text must be of type String parameters[\"login_help_text\"]");
    }

    if (parameters.containsKey("smtp_address") && !(parameters.get("smtp_address") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: smtp_address must be of type String parameters[\"smtp_address\"]");
    }

    if (parameters.containsKey("smtp_authentication") && !(parameters.get("smtp_authentication") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: smtp_authentication must be of type String parameters[\"smtp_authentication\"]");
    }

    if (parameters.containsKey("smtp_from") && !(parameters.get("smtp_from") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: smtp_from must be of type String parameters[\"smtp_from\"]");
    }

    if (parameters.containsKey("smtp_username") && !(parameters.get("smtp_username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: smtp_username must be of type String parameters[\"smtp_username\"]");
    }

    if (parameters.containsKey("smtp_port") && !(parameters.get("smtp_port") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: smtp_port must be of type Long parameters[\"smtp_port\"]");
    }

    if (parameters.containsKey("ldap_enabled") && !(parameters.get("ldap_enabled") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: ldap_enabled must be of type Boolean parameters[\"ldap_enabled\"]");
    }

    if (parameters.containsKey("ldap_type") && !(parameters.get("ldap_type") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_type must be of type String parameters[\"ldap_type\"]");
    }

    if (parameters.containsKey("ldap_host") && !(parameters.get("ldap_host") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_host must be of type String parameters[\"ldap_host\"]");
    }

    if (parameters.containsKey("ldap_host_2") && !(parameters.get("ldap_host_2") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_host_2 must be of type String parameters[\"ldap_host_2\"]");
    }

    if (parameters.containsKey("ldap_host_3") && !(parameters.get("ldap_host_3") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_host_3 must be of type String parameters[\"ldap_host_3\"]");
    }

    if (parameters.containsKey("ldap_port") && !(parameters.get("ldap_port") instanceof Long )) {
      throw new IllegalArgumentException("Bad parameter: ldap_port must be of type Long parameters[\"ldap_port\"]");
    }

    if (parameters.containsKey("ldap_secure") && !(parameters.get("ldap_secure") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: ldap_secure must be of type Boolean parameters[\"ldap_secure\"]");
    }

    if (parameters.containsKey("ldap_username") && !(parameters.get("ldap_username") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_username must be of type String parameters[\"ldap_username\"]");
    }

    if (parameters.containsKey("ldap_username_field") && !(parameters.get("ldap_username_field") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_username_field must be of type String parameters[\"ldap_username_field\"]");
    }

    if (parameters.containsKey("ldap_domain") && !(parameters.get("ldap_domain") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_domain must be of type String parameters[\"ldap_domain\"]");
    }

    if (parameters.containsKey("ldap_user_action") && !(parameters.get("ldap_user_action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_user_action must be of type String parameters[\"ldap_user_action\"]");
    }

    if (parameters.containsKey("ldap_group_action") && !(parameters.get("ldap_group_action") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_group_action must be of type String parameters[\"ldap_group_action\"]");
    }

    if (parameters.containsKey("ldap_user_include_groups") && !(parameters.get("ldap_user_include_groups") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_user_include_groups must be of type String parameters[\"ldap_user_include_groups\"]");
    }

    if (parameters.containsKey("ldap_group_exclusion") && !(parameters.get("ldap_group_exclusion") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_group_exclusion must be of type String parameters[\"ldap_group_exclusion\"]");
    }

    if (parameters.containsKey("ldap_group_inclusion") && !(parameters.get("ldap_group_inclusion") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_group_inclusion must be of type String parameters[\"ldap_group_inclusion\"]");
    }

    if (parameters.containsKey("ldap_base_dn") && !(parameters.get("ldap_base_dn") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_base_dn must be of type String parameters[\"ldap_base_dn\"]");
    }

    if (parameters.containsKey("icon16_file") && !(parameters.get("icon16_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: icon16_file must be of type byte[] parameters[\"icon16_file\"]");
    }

    if (parameters.containsKey("icon16_delete") && !(parameters.get("icon16_delete") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: icon16_delete must be of type Boolean parameters[\"icon16_delete\"]");
    }

    if (parameters.containsKey("icon32_file") && !(parameters.get("icon32_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: icon32_file must be of type byte[] parameters[\"icon32_file\"]");
    }

    if (parameters.containsKey("icon32_delete") && !(parameters.get("icon32_delete") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: icon32_delete must be of type Boolean parameters[\"icon32_delete\"]");
    }

    if (parameters.containsKey("icon48_file") && !(parameters.get("icon48_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: icon48_file must be of type byte[] parameters[\"icon48_file\"]");
    }

    if (parameters.containsKey("icon48_delete") && !(parameters.get("icon48_delete") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: icon48_delete must be of type Boolean parameters[\"icon48_delete\"]");
    }

    if (parameters.containsKey("icon128_file") && !(parameters.get("icon128_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: icon128_file must be of type byte[] parameters[\"icon128_file\"]");
    }

    if (parameters.containsKey("icon128_delete") && !(parameters.get("icon128_delete") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: icon128_delete must be of type Boolean parameters[\"icon128_delete\"]");
    }

    if (parameters.containsKey("logo_file") && !(parameters.get("logo_file") instanceof byte[] )) {
      throw new IllegalArgumentException("Bad parameter: logo_file must be of type byte[] parameters[\"logo_file\"]");
    }

    if (parameters.containsKey("logo_delete") && !(parameters.get("logo_delete") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: logo_delete must be of type Boolean parameters[\"logo_delete\"]");
    }

    if (parameters.containsKey("disable_2fa_with_delay") && !(parameters.get("disable_2fa_with_delay") instanceof Boolean )) {
      throw new IllegalArgumentException("Bad parameter: disable_2fa_with_delay must be of type Boolean parameters[\"disable_2fa_with_delay\"]");
    }

    if (parameters.containsKey("ldap_password_change") && !(parameters.get("ldap_password_change") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_password_change must be of type String parameters[\"ldap_password_change\"]");
    }

    if (parameters.containsKey("ldap_password_change_confirmation") && !(parameters.get("ldap_password_change_confirmation") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: ldap_password_change_confirmation must be of type String parameters[\"ldap_password_change_confirmation\"]");
    }

    if (parameters.containsKey("smtp_password") && !(parameters.get("smtp_password") instanceof String )) {
      throw new IllegalArgumentException("Bad parameter: smtp_password must be of type String parameters[\"smtp_password\"]");
    }

    String url = String.format("%s%s/site", FilesConfig.getInstance().getApiRoot(), FilesConfig.getInstance().getApiBase());
    TypeReference<Site> typeReference = new TypeReference<Site>() {};
    return FilesClient.requestItem(url, RequestMethods.PATCH, typeReference, parameters, options);
  }


}


