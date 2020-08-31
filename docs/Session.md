# Files.Models.Session

## Example Session Object

```
{
  "id": "60525f92e859c4c3d74cb02fd176b1525901b525",
  "language": "en",
  "login_token": "@tok-randomcode",
  "login_token_domain": "https://mysite.files.com",
  "max_dir_listing_size": 1,
  "multiple_regions": true,
  "read_only": true,
  "root_path": "",
  "site_id": 1,
  "ssl_required": true,
  "tls_disabled": true,
  "two_factor_setup_needed": true,
  "allowed_2fa_method_sms": true,
  "allowed_2fa_method_totp": true,
  "allowed_2fa_method_u2f": true,
  "allowed_2fa_method_yubi": true,
  "use_provided_modified_at": true,
  "windows_mode_ftp": true
}
```

* `id` / `id`  (String): Session ID
* `language` / `language`  (string): Session language
* `login_token` / `loginToken`  (string): Login token. If set, this token will allow your user to log in via browser at the domain in `login_token_domain`.
* `login_token_domain` / `loginTokenDomain`  (string): Domain to use with `login_token`.
* `max_dir_listing_size` / `maxDirListingSize`  (int64): Maximum number of files to retrieve per folder for a directory listing.  This is based on the user's plan.
* `multiple_regions` / `multipleRegions`  (boolean): Can access multiple regions?
* `read_only` / `readOnly`  (boolean): Is this session read only?
* `root_path` / `rootPath`  (string): Initial root path to start the user's session in.
* `site_id` / `siteId`  (int64): Site ID
* `ssl_required` / `sslRequired`  (boolean): Is SSL required for this user?  (If so, ensure all your communications with this user use SSL.)
* `tls_disabled` / `tlsDisabled`  (boolean): Is strong TLS disabled for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure TLS versions for this user.)
* `two_factor_setup_needed` / `twoFactorSetupNeeded`  (boolean): If true, this user needs to add a Two Factor Authentication method before performing any further actions.
* `allowed_2fa_method_sms` / `allowed2faMethodSms`  (boolean): Sent only if 2FA setup is needed. Is SMS two factor authentication allowed?
* `allowed_2fa_method_totp` / `allowed2faMethodTotp`  (boolean): Sent only if 2FA setup is needed. Is TOTP two factor authentication allowed?
* `allowed_2fa_method_u2f` / `allowed2faMethodU2f`  (boolean): Sent only if 2FA setup is needed. Is U2F two factor authentication allowed?
* `allowed_2fa_method_yubi` / `allowed2faMethodYubi`  (boolean): Sent only if 2FA setup is needed. Is Yubikey two factor authentication allowed?
* `use_provided_modified_at` / `useProvidedModifiedAt`  (boolean): Allow the user to provide file/folder modified at dates?  If false, the server will always use the current date/time.
* `windows_mode_ftp` / `windowsModeFtp`  (boolean): Does this user want to use Windows line-ending emulation?  (CR vs CRLF)
* `username` / `username`  (string): Username to sign in as
* `password` / `password`  (string): Password for sign in
* `otp` / `otp`  (string): If this user has a 2FA device, provide its OTP or code here.
* `partial_session_id` / `partialSessionId`  (string): Identifier for a partially-completed login


---

## Create user session (log in)

```
Session session = Session.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `username` (String): Username to sign in as
* `password` (String): Password for sign in
* `otp` (String): If this user has a 2FA device, provide its OTP or code here.
* `partial_session_id` (String): Identifier for a partially-completed login


---

## Delete user session (log out)

```
Session session = Session.delete(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `format` (String): 
* `session` (Map<String, String>): 
