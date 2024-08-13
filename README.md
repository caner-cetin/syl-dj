## Syl DJ

Not really a *dj*, but a music recommendation engine built in kotlin.

Work in progress. Currently you can:

- [x] Upload [AcousticBrainz](https://acousticbrainz.org/download) data dumps to system. 
  - [x] High Level Features
  - [x] Track Info 
  - [] Low Level Features
- [] ??


### Documentation
``/track/info/upload/highlevel``
High level features and **track info** (it is **mandatory** to provide both high level features
and track info from this endpoint) is uploaded in ``.tar`` format. so you can download
``.tar.zst`` dumps from AcousticBrainz, unzst, and upload the ``.tar`` directly.
```shell
$ curl -v -F upload=@acousticbrainz-highlevel-sample-json-20220623-0.tar http:/0.0.0.0:8080/track/info/upload/highlevel                                                                                                                                  
*   Trying 0.0.0.0:8080...
* Connected to 0.0.0.0 (0.0.0.0) port 8080
> POST /track/info/upload/highlevel HTTP/1.1
> Host: 0.0.0.0:8080
> User-Agent: curl/8.6.0
> Accept: */*
> Content-Length: 1133363457
> Content-Type: multipart/form-data; boundary=------------------------bUeq8oZRxkWjHAvkUEXuz7
> Expect: 100-continue
> 
< HTTP/1.1 100 Continue
* We are completely uploaded and fine
< HTTP/1.1 200 OK
< Content-Length: 1568
< Content-Type: application/json
```
Multiple ``.tar`` archives are supported.