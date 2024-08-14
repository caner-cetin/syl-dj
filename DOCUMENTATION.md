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

```shell
/album/cover/{mibd}
```
High level feature metadata contains respective MIBD (Musicbrainz ID) for songs, more specifically,
``album artist ID``,``album ID`` (please use this if you can.),``artist ID``,``recording ID``,``work ID``.
All types of ID's are saved into database when high level data is uploaded and can be queried by respective endpoints.

```shell
$ curl -v http:/0.0.0.0:8080/album/cover/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27
```
<details>
  <summary>Sample Output</summary>

  ```json
{
  "images": [
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 59951760,
      "front": true,
      "id": 22462761470,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462761470.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462761470-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462761470-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462761470-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462761470-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462761470-250.jpg"
      },
      "types": [
        "Front"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 59951763,
      "front": false,
      "id": 22462762100,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462762100.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462762100-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462762100-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462762100-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462762100-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462762100-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 59951769,
      "front": false,
      "id": 22462763241,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462763241.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462763241-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462763241-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462763241-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462763241-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462763241-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 59951772,
      "front": false,
      "id": 22462764420,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462764420.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462764420-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462764420-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462764420-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462764420-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/22462764420-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": true,
      "comment": "",
      "edit": 80338718,
      "front": false,
      "id": 29582305734,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582305734.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582305734-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582305734-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582305734-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582305734-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582305734-250.jpg"
      },
      "types": [
        "Back"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338719,
      "front": false,
      "id": 29582306568,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582306568.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582306568-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582306568-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582306568-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582306568-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582306568-250.jpg"
      },
      "types": [
        "Medium"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338720,
      "front": false,
      "id": 29582307678,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582307678.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582307678-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582307678-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582307678-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582307678-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582307678-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338721,
      "front": false,
      "id": 29582308208,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582308208.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582308208-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582308208-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582308208-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582308208-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582308208-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338722,
      "front": false,
      "id": 29582309019,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309019.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309019-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309019-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309019-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309019-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309019-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338723,
      "front": false,
      "id": 29582309966,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309966.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309966-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309966-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309966-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309966-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582309966-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338725,
      "front": false,
      "id": 29582310815,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582310815.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582310815-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582310815-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582310815-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582310815-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582310815-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338726,
      "front": false,
      "id": 29582311812,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582311812.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582311812-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582311812-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582311812-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582311812-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582311812-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338731,
      "front": false,
      "id": 29582312675,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582312675.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582312675-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582312675-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582312675-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582312675-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582312675-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338738,
      "front": false,
      "id": 29582313917,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582313917.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582313917-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582313917-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582313917-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582313917-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582313917-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338739,
      "front": false,
      "id": 29582314519,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582314519.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582314519-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582314519-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582314519-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582314519-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582314519-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338740,
      "front": false,
      "id": 29582315592,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582315592.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582315592-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582315592-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582315592-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582315592-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582315592-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338741,
      "front": false,
      "id": 29582316190,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582316190.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582316190-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582316190-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582316190-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582316190-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582316190-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    },
    {
      "approved": true,
      "back": false,
      "comment": "",
      "edit": 80338742,
      "front": false,
      "id": 29582317182,
      "image": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582317182.jpg",
      "thumbnails": {
        "1200": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582317182-1200.jpg",
        "250": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582317182-250.jpg",
        "500": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582317182-500.jpg",
        "large": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582317182-500.jpg",
        "small": "http://coverartarchive.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27/29582317182-250.jpg"
      },
      "types": [
        "Booklet"
      ]
    }
  ],
  "release": "https://musicbrainz.org/release/25fbfbb4-b1ee-4448-aadf-ae3bc2e2dd27"
}
```
</details>
