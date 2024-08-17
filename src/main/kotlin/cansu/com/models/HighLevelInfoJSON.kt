package cansu.com.models
import com.google.gson.annotations.SerializedName


data class HighLevelInfoJSON(
    val highlevel: Highlevel,
    val metadata: Metadata
) {
    data class Highlevel(
        val danceability: Danceability,
        val gender: Gender,
        val genre_dortmund: GenreDortmund,
        val genre_electronic: GenreElectronic,
        val genre_rosamerica: GenreRosamerica,
        val genre_tzanetakis: GenreTzanetakis,
        val ismir04_rhythm: Ismir04Rhythm,
        val mood_acoustic: MoodAcoustic,
        val mood_aggressive: MoodAggressive,
        val mood_electronic: MoodElectronic,
        val mood_happy: MoodHappy,
        val mood_party: MoodParty,
        val mood_relaxed: MoodRelaxed,
        val mood_sad: MoodSad,
        val moods_mirex: MoodsMirex,
        val timbre: Timbre,
        val tonal_atonal: TonalAtonal,
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
        val tags: Tags,
        val version: Version
    ) {
        data class AudioProperties(
            @SerializedName("analysis_sample_rate")
            val analysisSampleRate: Int,
            @SerializedName("bit_rate")
            val bitRate: Int,
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
            val album: List<String>,
            val albumartist: List<String>,
            val artist: List<String>,
            val date: List<String>,
            val discnumber: List<String>,
            @SerializedName("file_name")
            val fileName: String,
            @SerializedName("musicbrainz album type")
            val musicbrainzAlbumType: List<String>,
            @SerializedName("musicbrainz_albumartistid")
            val musicbrainzAlbumartistid: List<String>,
            @SerializedName("musicbrainz_albumid")
            val musicbrainzAlbumid: List<String>,
            @SerializedName("musicbrainz_artistid")
            val musicbrainzArtistid: List<String>,
            @SerializedName("musicbrainz_recordingid")
            val musicbrainzRecordingid: List<String>,
            @SerializedName("musicbrainz release track id")
            val musicbrainzReleaseTrackId: List<String>,
            @SerializedName("musicbrainz_releasegroupid")
            val musicbrainzReleasegroupid: List<String>,
            val title: List<String>,
            val tracknumber: List<String>
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