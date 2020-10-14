# Files.Models.SsoStrategy

## Example SsoStrategy Object

```
{
  "protocol": [

  ],
  "provider": "okta",
  "label": "My Corporate SSO Provider",
  "logo_url": "https://mysite.files.com/.../logo.png",
  "id": 1,
  "saml_provider_cert_fingerprint": "",
  "saml_provider_issuer_url": "",
  "saml_provider_metadata_content": "",
  "saml_provider_metadata_url": "",
  "saml_provider_slo_target_url": "",
  "saml_provider_sso_target_url": "",
  "scim_authentication_method": "",
  "scim_username": "",
  "subdomain": "my-site",
  "provision_users": true,
  "provision_groups": true,
  "deprovision_users": true,
  "deprovision_groups": true,
  "deprovision_behavior": "disable",
  "provision_group_default": "Employees",
  "provision_group_exclusion": "Employees",
  "provision_group_inclusion": "Employees",
  "provision_group_required": "",
  "provision_site_admin_groups": "Employees",
  "provision_attachments_permission": true,
  "provision_dav_permission": true,
  "provision_ftp_permission": true,
  "provision_sftp_permission": true,
  "provision_time_zone": "Eastern Time (US & Canada)",
  "provision_company": "ACME Corp.",
  "ldap_base_dn": "",
  "ldap_domain": "mysite.com",
  "enabled": true,
  "ldap_host": "ldap.site.com",
  "ldap_host_2": "ldap2.site.com",
  "ldap_host_3": "ldap3.site.com",
  "ldap_port": 1,
  "ldap_secure": true,
  "ldap_username": "[ldap username]",
  "ldap_username_field": "sAMAccountName"
}
```

* `protocol` / `protocol`  (array): SSO Protocol
* `provider` / `provider`  (string): Provider name
* `label` / `label`  (string): Custom label for the SSO provider on the login page.
* `logo_url` / `logoUrl`  (string): URL holding a custom logo for the SSO provider on the login page.
* `id` / `id`  (int64): ID
* `saml_provider_cert_fingerprint` / `samlProviderCertFingerprint`  (string): Identity provider sha256 cert fingerprint if saml_provider_metadata_url is not available.
* `saml_provider_issuer_url` / `samlProviderIssuerUrl`  (string): Identity provider issuer url
* `saml_provider_metadata_content` / `samlProviderMetadataContent`  (string): Custom identity provider metadata
* `saml_provider_metadata_url` / `samlProviderMetadataUrl`  (string): Metadata URL for the SAML identity provider
* `saml_provider_slo_target_url` / `samlProviderSloTargetUrl`  (string): Identity provider SLO endpoint
* `saml_provider_sso_target_url` / `samlProviderSsoTargetUrl`  (string): Identity provider SSO endpoint if saml_provider_metadata_url is not available.
* `scim_authentication_method` / `scimAuthenticationMethod`  (string): SCIM authentication type.
* `scim_username` / `scimUsername`  (string): SCIM username.
* `subdomain` / `subdomain`  (string): Subdomain
* `provision_users` / `provisionUsers`  (boolean): Auto-provision users?
* `provision_groups` / `provisionGroups`  (boolean): Auto-provision group membership based on group memberships on the SSO side?
* `deprovision_users` / `deprovisionUsers`  (boolean): Auto-deprovision users?
* `deprovision_groups` / `deprovisionGroups`  (boolean): Auto-deprovision group membership based on group memberships on the SSO side?
* `deprovision_behavior` / `deprovisionBehavior`  (string): Method used for deprovisioning users.
* `provision_group_default` / `provisionGroupDefault`  (string): Comma-separated list of group names for groups to automatically add all auto-provisioned users to.
* `provision_group_exclusion` / `provisionGroupExclusion`  (string): Comma-separated list of group names for groups (with optional wildcards) that will be excluded from auto-provisioning.
* `provision_group_inclusion` / `provisionGroupInclusion`  (string): Comma-separated list of group names for groups (with optional wildcards) that will be auto-provisioned.
* `provision_group_required` / `provisionGroupRequired`  (string): Comma or newline separated list of group names (with optional wildcards) to require membership for user provisioning.
* `provision_site_admin_groups` / `provisionSiteAdminGroups`  (string): Comma-separated list of group names whose members will be created as Site Admins.
* `provision_attachments_permission` / `provisionAttachmentsPermission`  (boolean): Auto-provisioned users get Sharing permission?
* `provision_dav_permission` / `provisionDavPermission`  (boolean): Auto-provisioned users get WebDAV permission?
* `provision_ftp_permission` / `provisionFtpPermission`  (boolean): Auto-provisioned users get FTP permission?
* `provision_sftp_permission` / `provisionSftpPermission`  (boolean): Auto-provisioned users get SFTP permission?
* `provision_time_zone` / `provisionTimeZone`  (string): Default time zone for auto provisioned users.
* `provision_company` / `provisionCompany`  (string): Default company for auto provisioned users.
* `ldap_base_dn` / `ldapBaseDn`  (string): Base DN for looking up users in LDAP server
* `ldap_domain` / `ldapDomain`  (string): Domain name that will be appended to LDAP usernames
* `enabled` / `enabled`  (boolean): Is strategy enabled?
* `ldap_host` / `ldapHost`  (string): LDAP host
* `ldap_host_2` / `ldapHost2`  (string): LDAP backup host
* `ldap_host_3` / `ldapHost3`  (string): LDAP backup host
* `ldap_port` / `ldapPort`  (int64): LDAP port
* `ldap_secure` / `ldapSecure`  (boolean): Use secure LDAP?
* `ldap_username` / `ldapUsername`  (string): Username for signing in to LDAP server.
* `ldap_username_field` / `ldapUsernameField`  (string): LDAP username field


---

## List Sso Strategies

```
List<SsoStrategy> ssoStrategy = SsoStrategy.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `page` (Long): Current page number.
* `per_page` (Long): Number of records to show per page.  (Max: 10,000, 1,000 or less is recommended).
* `action` (String): Deprecated: If set to `count` returns a count of matching records rather than the records themselves.
* `cursor` (String): Send cursor to resume an existing list from the point at which you left off.  Get a cursor from an existing list via the X-Files-Cursor-Next header.


---

## Show Sso Strategy

```
List<SsoStrategy> ssoStrategy = SsoStrategy.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Sso Strategy ID.