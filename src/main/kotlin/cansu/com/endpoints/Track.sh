# collections of upload scripts in curl
# https://data.metabrainz.org/pub/musicbrainz/data/fullexport
# https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/acousticbrainz-highlevel-json-20220623/
FTP_USER=sylftp
FTP_PASSWORD=b7c790faf3c5a7b230a3fff9cf9cb319
FTP_SERVER=ftp://localhost:2221
FTP_HOME_DIRECTORY=/sylftp/upload
HTTP_SERVER=http://0.0.0.0:8080

ABRAINZ_FOLDER_PATH=/Users/canercetin/Downloads/acousticbrainz
MBDUMP_FOLDER_PATH=/Users/canercetin/Downloads/mbdump
MBDUMP_DERIVED_FOLDER_PATH=/Users/canercetin/Downloads/mbdump-derived

curl -v --request POST -F upload=@$MBDUMP_FOLDER_PATH/mbdump/release "$HTTP_SERVER/upload/releases"
curl -v --request POST -F upload=@$MBDUMP_FOLDER_PATH/mbdump/genre "$HTTP_SERVER/upload/genres"
curl -v --request POST -F upload=@$MBDUMP_DERIVED_FOLDER_PATH/mbdump/tag "$HTTP_SERVER/upload/tags"
curl -v --request POST -F upload=@$MBDUMP_DERIVED_FOLDER_PATH/mbdump/release_tag "$HTTP_SERVER/upload/releases/tags"
curl -v --request POST -F upload=@$MBDUMP_DERIVED_FOLDER_PATH/mbdump/recording_tag "$HTTP_SERVER/upload/recording/tags"
#
#
#
curl --user "$FTP_USER:$FTP_PASSWORD" -T $MBDUMP_FOLDER_PATH/mbdump/recording $FTP_SERVER
curl -v --request POST "$HTTP_SERVER/upload/recording?fileLocation=$FTP_HOME_DIRECTORY/recording"
# https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/acousticbrainz-highlevel-json-20220623/
curl --user "$FTP_USER:$FTP_PASSWORD" -T $ABRAINZ_FOLDER_PATH/acousticbrainz-highlevel-json-20220623-0.tar $FTP_SERVER
curl -v --request POST "$HTTP_SERVER/upload/highlevel?fileLocation=$FTP_HOME_DIRECTORY/acousticbrainz-highlevel-sample-json-20220623-0.tar&deleteAfter=true"