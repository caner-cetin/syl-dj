package cansu.com.models
import com.google.gson.annotations.SerializedName

data class HighLevelInfoJSON(
    val highlevel: Highlevel,
    val metadata: Metadata?
) {
    data class Highlevel(
        val danceability: Danceability,
        val gender: Gender,
        @SerializedName("genre_dortmund")
        val genre_dortmund: GenreDortmund,
        @SerializedName("genre_electronic")
        val genre_electronic: GenreElectronic,
        @SerializedName("genre_rosamerica")
        val genre_rosamerica: GenreRosamerica,
        @SerializedName("genre_tzanetakis")
        val genre_tzanetakis: GenreTzanetakis,
        @SerializedName("ismir04_rhythm")
        val ismir04_rhythm: Ismir04Rhythm,
        @SerializedName("mood_acoustic")
        val mood_acoustic: MoodAcoustic,
        @SerializedName("mood_aggressive")
        val mood_aggressive: MoodAggressive,
        @SerializedName("mood_electronic")
        val mood_electronic: MoodElectronic,
        @SerializedName("mood_happy")
        val mood_happy: MoodHappy,
        @SerializedName("mood_party")
        val mood_party: MoodParty,
        @SerializedName("mood_relaxed")
        val mood_relaxed: MoodRelaxed,
        @SerializedName("mood_sad")
        val mood_sad: MoodSad,
        @SerializedName("moods_mirex")
        val moods_mirex: MoodsMirex?,
        val timbre: Timbre,
        @SerializedName("tonal_atonal")
        val tonal_atonal: TonalAtonal,
        @SerializedName("voice_instrumental")
        val voice_instrumental: VoiceInstrumental
    ) {
        data class Danceability(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val danceable: Double,
                @SerializedName("not_danceable")
                val notDanceable: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class Gender(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val female: Double,
                val male: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class GenreDortmund(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val alternative: Double,
                val blues: Double,
                val electronic: Double,
                val folkcountry: Double,
                val funksoulrnb: Double,
                val jazz: Double,
                val pop: Double,
                val raphiphop: Double,
                val rock: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class GenreElectronic(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val ambient: Double,
                val dnb: Double,
                val house: Double,
                val techno: Double,
                val trance: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class GenreRosamerica(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val cla: Double,
                val dan: Double,
                val hip: Double,
                val jaz: Double,
                val pop: Double,
                val rhy: Double,
                val roc: Double,
                val spe: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class GenreTzanetakis(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val blu: Double,
                val cla: Double,
                val cou: Double,
                val dis: Double,
                val hip: Double,
                val jaz: Double,
                val met: Double,
                val pop: Double,
                val reg: Double,
                val roc: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class Ismir04Rhythm(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                @SerializedName("ChaChaCha")
                val chaChaCha: Double,
                @SerializedName("Jive")
                val jive: Double,
                @SerializedName("Quickstep")
                val quickstep: Double,
                @SerializedName("Rumba-American")
                val rumbaAmerican: Double,
                @SerializedName("Rumba-International")
                val rumbaInternational: Double,
                @SerializedName("Rumba-Misc")
                val rumbaMisc: Double,
                @SerializedName("Samba")
                val samba: Double,
                @SerializedName("Tango")
                val tango: Double,
                @SerializedName("VienneseWaltz")
                val vienneseWaltz: Double,
                @SerializedName("Waltz")
                val waltz: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodAcoustic(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val acoustic: Double,
                @SerializedName("not_acoustic")
                val notAcoustic: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodAggressive(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val aggressive: Double,
                @SerializedName("not_aggressive")
                val notAggressive: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodElectronic(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val electronic: Double,
                @SerializedName("not_electronic")
                val notElectronic: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodHappy(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val happy: Double,
                @SerializedName("not_happy")
                val notHappy: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodParty(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                @SerializedName("not_party")
                val notParty: Double,
                val party: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodRelaxed(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                @SerializedName("not_relaxed")
                val notRelaxed: Double,
                val relaxed: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodSad(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                @SerializedName("not_sad")
                val notSad: Double,
                val sad: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodsMirex(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                @SerializedName("Cluster1")
                val cluster1: Double,
                @SerializedName("Cluster2")
                val cluster2: Double,
                @SerializedName("Cluster3")
                val cluster3: Double,
                @SerializedName("Cluster4")
                val cluster4: Double,
                @SerializedName("Cluster5")
                val cluster5: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class Timbre(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val bright: Double,
                val dark: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class TonalAtonal(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val atonal: Double,
                val tonal: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class VoiceInstrumental(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val instrumental: Double,
                val voice: Double
            )

            data class Version(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }
    }

    data class Metadata(
        @SerializedName("audio_properties")
        val audioProperties: AudioProperties,
        val tags: Tags?,
        val version: Version
    ) {
        data class AudioProperties(
            @SerializedName("analysis_sample_rate")
            val analysisSampleRate: Int,
            @SerializedName("bit_rate")
            val bitRate: Double,
            val codec: String,
            val downmix: String,
            @SerializedName("equal_loudness")
            val equalLoudness: Int,
            val length: Double,
            val lossless: Int,
            @SerializedName("md5_encoded")
            val md5Encoded: String,
            @SerializedName("replay_gain")
            val replayGain: Double
        )

        data class Tags(
            @SerializedName("acoustid_id")
            val acoustidId: List<String>?,
            val album: List<String>?,
            val albumartist: List<String>?,
            val albumartistsort: List<String>?,
            val albumsort: List<String>?,
            val arranger: List<String>?,
            val artist: List<String>?,
            val artists: List<String>?,
            val artistsort: List<String>?,
            val catalognumber: List<String>?,
            val composer: List<String>?,
            val composersort: List<String>?,
            val date: List<String>?,
            val discnumber: List<String>?,
            val disctotal: List<String>?,
            @SerializedName("file_name")
            val fileName: String,
            val lyricist: List<String>,
            val media: List<String>,
            @SerializedName("musicbrainz_albumartistid")
            val musicbrainzAlbumartistid: List<String>,
            @SerializedName("musicbrainz_albumid")
            val musicbrainzAlbumid: List<String>?,
            @SerializedName("musicbrainz_artistid")
            val musicbrainzArtistid: List<String>,
            @SerializedName("musicbrainz_recordingid")
            val musicbrainzRecordingid: List<String>?,
            @SerializedName("musicbrainz_releasegroupid")
            val musicbrainzReleasegroupid: List<String>?,
            @SerializedName("musicbrainz_releasetrackid")
            val musicbrainzReleasetrackid: List<String>?,
            @SerializedName("musicbrainz_workid")
            val musicbrainzWorkid: List<String>,
            val originaldate: List<String>,
            val performer: List<String>,
            val releasecountry: List<String>,
            val releasestatus: List<String>,
            val releasetype: List<String>,
            @SerializedName("replaygain_album_gain")
            val replaygainAlbumGain: List<String>,
            @SerializedName("replaygain_album_peak")
            val replaygainAlbumPeak: List<String>,
            @SerializedName("replaygain_track_gain")
            val replaygainTrackGain: List<String>,
            @SerializedName("replaygain_track_peak")
            val replaygainTrackPeak: List<String>,
            val script: List<String>,
            val title: List<String>?,
            val totaldiscs: List<String>,
            val totaltracks: List<String>,
            val tracknumber: List<String>,
            val tracktotal: List<String>,
            val work: List<String>
        )

        data class Version(
            val highlevel: Highlevel,
            val lowlevel: Lowlevel
        ) {
            data class Highlevel(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @SerializedName("gaia_git_sha")
                val gaiaGitSha: String,
                @SerializedName("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )

            data class Lowlevel(
                val essentia: String,
                @SerializedName("essentia_build_sha")
                val essentiaBuildSha: String,
                @SerializedName("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String
            )
        }
    }
}