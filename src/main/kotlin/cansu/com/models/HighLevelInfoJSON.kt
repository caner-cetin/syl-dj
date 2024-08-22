package cansu.com.models
import com.fasterxml.jackson.annotation.JsonProperty


data class HighLevelInfoJSON(
    val highlevel: Highlevel,
    val metadata: Metadata?
) {
    data class Highlevel(
        val danceability: Danceability,
        val gender: Gender,
        @JsonProperty("genre_dortmund")
        val genre_dortmund: GenreDortmund,
        @JsonProperty("genre_electronic")
        val genre_electronic: GenreElectronic,
        @JsonProperty("genre_rosamerica")
        val genre_rosamerica: GenreRosamerica,
        @JsonProperty("genre_tzanetakis")
        val genre_tzanetakis: GenreTzanetakis,
        @JsonProperty("ismir04_rhythm")
        val ismir04_rhythm: Ismir04Rhythm,
        @JsonProperty("mood_acoustic")
        val mood_acoustic: MoodAcoustic,
        @JsonProperty("mood_aggressive")
        val mood_aggressive: MoodAggressive,
        @JsonProperty("mood_electronic")
        val mood_electronic: MoodElectronic,
        @JsonProperty("mood_happy")
        val mood_happy: MoodHappy,
        @JsonProperty("mood_party")
        val mood_party: MoodParty,
        @JsonProperty("mood_relaxed")
        val mood_relaxed: MoodRelaxed,
        @JsonProperty("mood_sad")
        val mood_sad: MoodSad,
        @JsonProperty("moods_mirex")
        val moods_mirex: MoodsMirex?,
        val timbre: Timbre,
        @JsonProperty("tonal_atonal")
        val tonal_atonal: TonalAtonal,
        @JsonProperty("voice_instrumental")
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
                @JsonProperty("not_danceable")
                val notDanceable: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("ChaChaCha")
                val chaChaCha: Double,
                @JsonProperty("Jive")
                val jive: Double,
                @JsonProperty("Quickstep")
                val quickstep: Double,
                @JsonProperty("Rumba-American")
                val rumbaAmerican: Double,
                @JsonProperty("Rumba-International")
                val rumbaInternational: Double,
                @JsonProperty("Rumba-Misc")
                val rumbaMisc: Double,
                @JsonProperty("Samba")
                val samba: Double,
                @JsonProperty("Tango")
                val tango: Double,
                @JsonProperty("VienneseWaltz")
                val vienneseWaltz: Double,
                @JsonProperty("Waltz")
                val waltz: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodAcoustic(
            val all: All,
            val probability: Int,
            val value: String,
            val version: Version
        ) {
            data class All(
                val acoustic: Double,
                @JsonProperty("not_acoustic")
                val notAcoustic: Int
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }

        data class MoodAggressive(
            val all: All,
            val probability: Int,
            val value: String,
            val version: Version
        ) {
            data class All(
                val aggressive: Double,
                @JsonProperty("not_aggressive")
                val notAggressive: Int
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("not_electronic")
                val notElectronic: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("not_happy")
                val notHappy: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("not_party")
                val notParty: Double,
                val party: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("not_relaxed")
                val notRelaxed: Double,
                val relaxed: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("not_sad")
                val notSad: Double,
                val sad: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("Cluster1")
                val cluster1: Double,
                @JsonProperty("Cluster2")
                val cluster2: Double,
                @JsonProperty("Cluster3")
                val cluster3: Double,
                @JsonProperty("Cluster4")
                val cluster4: Double,
                @JsonProperty("Cluster5")
                val cluster5: Double
            )

            data class Version(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }
    }

    data class Metadata(
        @JsonProperty("audio_properties")
        val audioProperties: AudioProperties,
        val tags: Tags,
        val version: Version
    ) {
        data class AudioProperties(
            @JsonProperty("analysis_sample_rate")
            val analysisSampleRate: Int,
            @JsonProperty("bit_rate")
            val bitRate: Int,
            val codec: String,
            val downmix: String,
            @JsonProperty("equal_loudness")
            val equalLoudness: Int,
            val length: Double,
            val lossless: Int,
            @JsonProperty("md5_encoded")
            val md5Encoded: String,
            @JsonProperty("replay_gain")
            val replayGain: Double
        )

        data class Tags(
            @JsonProperty("acoustid_id")
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
            @JsonProperty("file_name")
            val fileName: String,
            val lyricist: List<String>,
            val media: List<String>,
            @JsonProperty("musicbrainz_albumartistid")
            val musicbrainzAlbumartistid: List<String>,
            @JsonProperty("musicbrainz_albumid")
            val musicbrainzAlbumid: List<String>?,
            @JsonProperty("musicbrainz_artistid")
            val musicbrainzArtistid: List<String>,
            @JsonProperty("musicbrainz_recordingid")
            val musicbrainzRecordingid: List<String>?,
            @JsonProperty("musicbrainz_releasegroupid")
            val musicbrainzReleasegroupid: List<String>?,
            @JsonProperty("musicbrainz_releasetrackid")
            val musicbrainzReleasetrackid: List<String>?,
            @JsonProperty("musicbrainz_workid")
            val musicbrainzWorkid: List<String>,
            val originaldate: List<String>,
            val performer: List<String>,
            val releasecountry: List<String>,
            val releasestatus: List<String>,
            val releasetype: List<String>,
            @JsonProperty("replaygain_album_gain")
            val replaygainAlbumGain: List<String>,
            @JsonProperty("replaygain_album_peak")
            val replaygainAlbumPeak: List<String>,
            @JsonProperty("replaygain_track_gain")
            val replaygainTrackGain: List<String>,
            @JsonProperty("replaygain_track_peak")
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
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JsonProperty("gaia_git_sha")
                val gaiaGitSha: String,
                @JsonProperty("models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )

            data class Lowlevel(
                val essentia: String,
                @JsonProperty("essentia_build_sha")
                val essentiaBuildSha: String,
                @JsonProperty("essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String
            )
        }
    }
}