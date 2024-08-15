# Syl DJ

Not really a *dj*, but a music recommendation engine built in kotlin.

**Work in progress.**

- [x] Upload [AcousticBrainz](https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/acousticbrainz-highlevel-json-20220623/) data dumps to system. 
  - [x] High Level Features
  - [x] Track Info 
  - [x] MIREX Data supported with `pgvector` database for nearest neighbor search in moods
  - [x] Small-medium sized TAR archive support from [HTTP/POST body](https://everything.curl.dev/http/post/binary.html) directly
  - [ ] Large sized TAR archive support with FTP
  - [ ] Password protection for upload methods in both HTTP route and FTP server
- [ ] Upload [MusicBrainz](https://wiki.musicbrainz.org/Main_Page) data dumps to system.
  - [x] Release Info (id, gid, name, language ID)s
- [x] Remote debug and VisualVM support
- [x] Album cover query API

### Documentation
wip