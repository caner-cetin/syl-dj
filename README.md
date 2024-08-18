# Syl DJ

Not really a *dj*, but a music recommendation engine built in kotlin.

**Work in progress.**

- [x] Upload [AcousticBrainz](https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/acousticbrainz-highlevel-json-20220623/) data dumps to system.
  - [x] High Level Features
  - [x] Track Info 
  - [x] MIREX Data supported with `pgvector` database for nearest neighbor search in moods
  - [x] Small-medium sized TAR archive support from [HTTP/POST body](https://everything.curl.dev/http/post/binary.html) directly
  - [x] Large sized TAR archive support with FTP (behind authentication)
  - [X] Optimized memory usage, can process 10 GB tar dump files with 1GB of heap space.
  - [X] Optimized speed, can process and store 1 million tracks and MIREX clusters, 17.2 million track attributes in 10 gb tarball within 5+- minutes.
- [ ] Upload [MusicBrainz](https://wiki.musicbrainz.org/Main_Page) data dumps to system.
  - [x] Release Info (id, gid, name, language ID)s
- [x] Remote debug and VisualVM support
- [x] Album cover query API

### Notes

- there is no authentication (ftp has auth), there will be no frontend application, it is just solely backend because I am so
burnt out on usual "CRUD" full stack apps, and sick of doing user management over and over again.


### Documentation
wip