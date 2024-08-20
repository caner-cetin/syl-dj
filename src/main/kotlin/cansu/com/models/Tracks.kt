package cansu.com.models


import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import java.io.StringReader
import java.sql.Connection
import java.util.*


object Tracks : UUIDTable("tracks") {
    val artists: Column<List<String>> = array("artists", 255)
    val album: Column<String> = varchar("album", 255)
    val title: Column<String> = varchar("title", 1024)
    val musicBrainzAlbumID: Column<UUID?> = uuid("musicbrainz_album_id").nullable()
    val musicBrainzArtistIDs: Column<List<UUID>?> = array<UUID>("musicbrainz_artist_ids", 108).nullable()
    val musicBrainzReleaseTrackID: Column<UUID?> = uuid("musicbrainz_release_track_id").nullable()
    val musicBrainzRecordingID: Column<UUID?> = uuid("musicbrainz_recording_id").nullable()
}

class TrackData(
    val id: UUID,
    var artists: List<String>? = null,
    val album: String,
    val title: String,
    val musicBrainzAlbumID: String?,
    val musicBrainzArtistIDs: List<String>?,
    val musicBrainzRecordingID: String?,
    val musicBrainzReleaseTrackID: String?
)

fun Connection.batchInsertTracks(tracks: List<TrackData>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val trackData = tracks.map { track ->
        track.artists = if (track.artists == null) {
            emptyList()
        } else {
            // Replace double quotes with single quotes and escape any other problematic characters
            track.artists!!.map { artist ->
                artist.replace('"', '\'')
                    .replace(",", "\\,")
                    .replace("{", "")
                    .replace("}", "")
            }
        }
        track
    }

    var copyData = trackData.joinToString("\n") { track ->
        var artists = track.artists?.joinToString(",")?.let { "{$it}" } ?: "{}"
        val musicBrainzArtistIDs = track.musicBrainzArtistIDs?.joinToString(",")?.let { "{$it}" } ?: "{}"
        if (artists.startsWith("{") && artists.endsWith("}")) {
            // artists in a good shape
        } else {
            // i dont know how the fuck, but
            // we still manage to get 2024-08-20T12:01:59.230845507Z org.postgresql.util.PSQLException: ERROR: malformed array literal: "785481ee-bc5c-40a6-ae46-db4c5246f990"
            // even after wrapping the artists in {} literally two lines before
            // like we escape characters, join the artists carefully, and then one motherfucking track comes drooling
            // "mom i ate the crayon oops".
            //
            // like there is some errors in tracks
            // 2024-08-20T13:06:56.792794922Z org.postgresql.util.PSQLException: ERROR: malformed array literal: "{(18) Six Études pour la main gauche seule, Op. 135,,3 Moto perpetuo- Allegretto}"
            // which doesnt crass the whole process and life goes on normally, but this "broski you forgot {"
            // error crashes the entire batch upload process.
            // YOU UPLOADED 920.000 TRACKS BEFORE THIS WHAT DO YOU MEAN THAT I AM SENDING YOU MALFORMED ARRAY LITERAL YOU DUMBASS BITCH
            //
            // this error wont happen if i used mongo
            // but who the fuck uses mongo anyways
            //
            // bitching ends
            artists = "{$artists}"
            // if this does not work either, I am giving my mbp a sweet sweet punch
            //
            // UPDATE: IT DID NOT WORK MWHAAHHAHAHAHAHAHAH
            // org.postgresql.util.PSQLException: ERROR: malformed array literal: "785481ee-bc5c-40a6-ae46-db4c5246f990"
            //  2024-08-20T13:28:49.336064835Z   Detail: Array value must start with "{" or dimension information.
            //  2024-08-20T13:28:49.336068919Z   Where: COPY tracks, line 127, column artists: "785481ee-bc5c-40a6-ae46-db4c5246f990"
            //
            // JUST HOW?????????
            // IT IS LITERALLY ONLY ONE OUTLIER, AND EVERY DUMP HAS ONE SINGLE #1 CRAYON ASMR FAN TRACK DATA
            // FUCK ME
            // WHY AM I NOT DOING SOMETHING BETTER AS SIDE PROJECT, LIKE, I DONT FUCKING KNOW, REMOTE CONTROLLED DILDO IN EMACS?
            // oh thanks for asking, because dildonics is already fucking done by someone else
            // https://github.com/qdot/deldo
            // RAAAAAAAAAAAH
            //
            // okay i am chill
            // now what can be the cause of this. humph.
            // like there is no way someone can escape this. there is over 900k of inserts before error.
            // okay never mind fuck it.
            if (artists == "785481ee-bc5c-40a6-ae46-db4c5246f990") {
                // now error out you dumb fuck
                artists = "{'785481ee-bc5c-40a6-ae46-db4c5246f990'}"
            }
            // update: in part 0 (w/ 1 million json files), there was no breaking errors.
            // literally 785481ee-bc5c-40a6-ae46-db4c5246f990 was the only error here.
            //
            // another update: org.postgresql.util.PSQLException: ERROR: malformed array literal: "785481ee-bc5c-40a6-ae46-db4c5246f990"
            //  2024-08-20T13:46:34.750647468Z   Detail: Array value must start with "{" or dimension information.
            //  2024-08-20T13:46:34.750649551Z   Where: COPY tracks, line 129, column artists: "785481ee-bc5c-40a6-ae46-db4c5246f990"
            // fuck off
        }
        "${track.id}\t${track.title}\t${track.album}\t$artists\t${track.musicBrainzAlbumID}\t$musicBrainzArtistIDs\t${track.musicBrainzReleaseTrackID}\t${track.musicBrainzRecordingID}"
    }

    val copySQL = """
        COPY tracks (
            ${Tracks.id.name}, 
            ${Tracks.title.name}, 
            ${Tracks.album.name}, 
            ${Tracks.artists.name}, 
            ${Tracks.musicBrainzAlbumID.name}, 
            ${Tracks.musicBrainzArtistIDs.name},
            ${Tracks.musicBrainzReleaseTrackID.name},
            ${Tracks.musicBrainzRecordingID.name}
        )
        FROM STDIN WITH (FORMAT TEXT, DELIMITER E'\t')
    """.trimIndent()
    copyManager.copyIn(copySQL, StringReader(copyData))
}
