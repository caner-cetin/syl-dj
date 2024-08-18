# Syl DJ

Not really a *dj*, but a music recommendation engine built in kotlin.

**Work in progress.**

I have spent so much more time than I am willing to admit for file uploads. three or four full days only went to integrating
acousticbrainz dumps lmfao
- [x] Upload [AcousticBrainz](https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/acousticbrainz-highlevel-json-20220623/) data dumps to system.
  - [x] High Level Features
  - [x] Track Info 
  - [x] MIREX Data supported with `pgvector` database for nearest neighbor search in moods
  - [x] Small-medium sized TAR archive support from [HTTP/POST body](https://everything.curl.dev/http/post/binary.html) directly
  - [x] Large sized TAR archive support with FTP
  - [ ] Password protection for upload methods in both HTTP route and FTP server
    - [X] FTP
    - [ ] HTTP
  - [X] Optimized memory usage, can process 10 GB tar dump files with 1GB of heap space.
  - [X] Optimized speed, can process and store 1 million tracks and MIREX clusters, 17.2 million track attributes in 10 gb tarball within 5+- minutes.
- [ ] Upload [MusicBrainz](https://wiki.musicbrainz.org/Main_Page) data dumps to system.
  - [x] Release Info (id, gid, name, language ID)s
- [x] Remote debug and VisualVM support
- [x] Album cover query API

### Documentation
wip