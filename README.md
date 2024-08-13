## Syl DJ

Not really a *dj*, but a music recommendation engine built in kotlin.

Work in progress. Currently you can:

- [x] Upload [AcousticBrainz](https://acousticbrainz.org/download) data dumps to system. 
  - [x] High Level Features
  - [x] Track Info 
  - [ ] Low Level Features
- [ ] ??


### Documentation
```shell
/track/info/upload/highlevel
```
High level features and **track info** (it is **mandatory** to provide both high level features
and track info from this endpoint) is uploaded in ``.tar`` format. so you can download
``.tar.zst`` dumps from AcousticBrainz, unzst, and upload the ``.tar`` directly. Multiple ``.tar`` archives are supported.

```shell
$ curl -v -F upload=@acousticbrainz-highlevel-sample-json-20220623-0.tar http:/0.0.0.0:8080/track/info/upload/highlevel                                                                                                                                  
```
<details>
  <summary>Sample Output</summary>

  ```json
  {
  "errors": [
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/fc/a/fca75640-e3c4-4ac9-b702-022e8e066661-0.json",
      "error": {
        "first": "DJ-0006",
        "second": "Metadata in uploaded JSON is not an 'Object'."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/fc/a/fca75640-e3c4-4ac9-b702-022e8e066661-0.json",
      "error": {
        "first": "DJ-0006",
        "second": "Metadata in uploaded JSON is not an 'Object'."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/fc/a/fca75640-e3c4-4ac9-b702-022e8e066661-0.json",
      "error": {
        "first": "DJ-0006",
        "second": "Metadata in uploaded JSON is not an 'Object'."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/fc/a/fca75640-e3c4-4ac9-b702-022e8e066661-0.json",
      "error": {
        "first": "DJ-0006",
        "second": "Metadata in uploaded JSON is not an 'Object'."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/2c/c/2cc851fb-a40d-4fdb-ab55-e663d30b50f7-0.json",
      "error": {
        "first": "DJ-0002",
        "second": "Album name is required for submitting a track."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/4f/3/4f325ec9-f808-447c-a75c-6f89d27f7bab-0.json",
      "error": {
        "first": "DJ-0002",
        "second": "Album name is required for submitting a track."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/eb/8/eb863928-2d76-495b-9424-89dc0c2e03c8-0.json",
      "error": {
        "first": "DJ-0002",
        "second": "Album name is required for submitting a track."
      }
    },
    {
      "filename": "acousticbrainz-highlevel-sample-json-20220623/highlevel/8c/c/8cc400d5-78e4-4721-a880-7d655b11ecd3-0.json",
      "error": {
        "first": "DJ-0002",
        "second": "Album name is required for submitting a track."
      }
    }
  ]
}
```
</details>