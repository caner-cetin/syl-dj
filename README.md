# Syl DJ

Not really a *dj*, but a music recommendation engine built in kotlin.

**Work in progress.**

- [x] Upload [AcousticBrainz](https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/acousticbrainz-highlevel-json-20220623/) data dumps to system. 
  - [x] High Level Features
  - [x] Track Info 
  - [x] MIREX Data supported with `pgvector` database for nearest neighbor search in moods
  - [x] Small-medium sized TAR archive support from [HTTP/POST body](https://everything.curl.dev/http/post/binary.html) directly
  - [x] Large sized TAR archive support with FTP
  - [ ] Password protection for upload methods in both HTTP route and FTP server
    - [X] FTP
    - [ ] HTTP
  - [X] Optimized memory usage, can process 10 GB tar dump files with 2GB heap space, can do further optimizations and 
    reduce the heap space to 1GB. Not the first and most priority right now.
- [ ] Upload [MusicBrainz](https://wiki.musicbrainz.org/Main_Page) data dumps to system.
  - [x] Release Info (id, gid, name, language ID)s
- [x] Remote debug and VisualVM support
- [x] Album cover query API

### Documentation
wip