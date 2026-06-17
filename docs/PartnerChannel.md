# Files.Models.PartnerChannel

## Example PartnerChannel Object

```
{
  "id": 1,
  "workspace_id": 1,
  "partner_id": 1,
  "path": "claims/medical",
  "to_partner_folder_name": "incoming",
  "from_partner_folder_name": "outgoing",
  "from_partner_route_path": "processing/from-partner",
  "to_partner_route_path": "delivery/to-partner",
  "effective_to_partner_folder_name": "incoming",
  "effective_from_partner_folder_name": "outgoing",
  "channel_path": "partners/acme/claims/medical",
  "to_partner_folder_path": "partners/acme/claims/medical/incoming",
  "from_partner_folder_path": "partners/acme/claims/medical/outgoing"
}
```

* `id` / `id`  (int64): The unique ID of the Partner Channel.
* `workspace_id` / `workspaceId`  (int64): ID of the Workspace associated with this Partner Channel.
* `partner_id` / `partnerId`  (int64): ID of the Partner this Channel belongs to.
* `path` / `path`  (string): Channel path relative to the Partner root folder. This must be slash-delimited, but it must neither start nor end with a slash. Maximum of 5000 characters.
* `to_partner_folder_name` / `toPartnerFolderName`  (string): Optional Channel-level to-Partner folder name override.
* `from_partner_folder_name` / `fromPartnerFolderName`  (string): Optional Channel-level from-Partner folder name override.
* `from_partner_route_path` / `fromPartnerRoutePath`  (string): Optional route path for files uploaded by the Partner.
* `to_partner_route_path` / `toPartnerRoutePath`  (string): Optional route path for files delivered to the Partner.
* `effective_to_partner_folder_name` / `effectiveToPartnerFolderName`  (string): Resolved to-Partner folder name after Channel override and default.
* `effective_from_partner_folder_name` / `effectiveFromPartnerFolderName`  (string): Resolved from-Partner folder name after Channel override and default.
* `channel_path` / `channelPath`  (string): Resolved Channel folder path.
* `to_partner_folder_path` / `toPartnerFolderPath`  (string): Resolved to-Partner folder path.
* `from_partner_folder_path` / `fromPartnerFolderPath`  (string): Resolved from-Partner folder path.


---

## List Partner Channels

```
ListIterator<PartnerChannel> partnerChannel = PartnerChannel.list(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `cursor` (String): Used for pagination.  When a list request has more records available, cursors are provided in the response headers `X-Files-Cursor-Next` and `X-Files-Cursor-Prev`.  Send one of those cursor value here to resume an existing list from the next available record.  Note: many of our SDKs have iterator methods that will automatically handle cursor-based pagination.
* `per_page` (Long): Number of records to show per page.  (Max: 10000, 1,000 or less is recommended).
* `sort_by` (Object): If set, sort records by the specified field in either `asc` or `desc` direction. Valid fields are `workspace_id`, `path` or `partner_id`.
* `filter` (Object): If set, return records where the specified field is equal to the supplied value. Valid fields are `partner_id` and `workspace_id`. Valid field combinations are `[ workspace_id, partner_id ]`.


---

## Show Partner Channel

```
PartnerChannel partnerChannel = PartnerChannel.find(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Channel ID.


---

## Create Partner Channel

```
PartnerChannel partnerChannel = PartnerChannel.create(
    
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `from_partner_folder_name` (String): Optional Channel-level from-Partner folder name override.
* `from_partner_route_path` (String): Optional route path for files uploaded by the Partner.
* `to_partner_folder_name` (String): Optional Channel-level to-Partner folder name override.
* `to_partner_route_path` (String): Optional route path for files delivered to the Partner.
* `partner_id` (Long): Required - ID of the Partner this Channel belongs to.
* `path` (String): Required - Channel path relative to the Partner root folder.
* `workspace_id` (Long): ID of the Workspace associated with this Partner Channel.


---

## Update Partner Channel

```
PartnerChannel partnerChannel = PartnerChannel.update(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Channel ID.
* `from_partner_folder_name` (String): Optional Channel-level from-Partner folder name override.
* `from_partner_route_path` (String): Optional route path for files uploaded by the Partner.
* `to_partner_folder_name` (String): Optional Channel-level to-Partner folder name override.
* `to_partner_route_path` (String): Optional route path for files delivered to the Partner.
* `path` (String): Channel path relative to the Partner root folder.


---

## Delete Partner Channel

```
void partnerChannel = PartnerChannel.delete(
    Long id, 
    HashMap<String, Object> parameters = null,
    HashMap<String, Object> options = null
)
```

### Parameters

* `id` (Long): Required - Partner Channel ID.


---

## Update Partner Channel

```
PartnerChannel partnerChannel = PartnerChannel.find(id);

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("from_partner_folder_name", "outgoing");
parameters.put("from_partner_route_path", "processing/from-partner");
parameters.put("to_partner_folder_name", "incoming");
parameters.put("to_partner_route_path", "delivery/to-partner");
parameters.put("path", "claims/medical");

partnerChannel.update(parameters);
```

### Parameters

* `id` (Long): Required - Partner Channel ID.
* `from_partner_folder_name` (String): Optional Channel-level from-Partner folder name override.
* `from_partner_route_path` (String): Optional route path for files uploaded by the Partner.
* `to_partner_folder_name` (String): Optional Channel-level to-Partner folder name override.
* `to_partner_route_path` (String): Optional route path for files delivered to the Partner.
* `path` (String): Channel path relative to the Partner root folder.


---

## Delete Partner Channel

```
PartnerChannel partnerChannel = PartnerChannel.find(id);

HashMap<String, Object> parameters = new HashMap<>();

partnerChannel.delete(parameters);
```

### Parameters

* `id` (Long): Required - Partner Channel ID.
