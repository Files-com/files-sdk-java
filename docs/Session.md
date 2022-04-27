# Files.Models.Session

## Example Session Object

```
{
  "id": "60525f92e859c4c3d74cb02fd176b1525901b525",
  "language": "en",
  "read_only": true,
  "sftp_insecure_ciphers": true
}
```

* `id` / `id`  (String): Session ID
* `language` / `language`  (string): Session language
* `read_only` / `readOnly`  (boolean): Is this session read only?
* `sftp_insecure_ciphers` / `sftpInsecureCiphers`  (boolean): Are insecure SFTP ciphers allowed for this user? (If this is set to true, the site administrator has signaled that it is ok to use less secure SSH ciphers for this user.)
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
