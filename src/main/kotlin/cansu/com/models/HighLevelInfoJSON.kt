package cansu.com.models

import com.alibaba.fastjson2.annotation.JSONField


data class HighLevelInfoJSON(
    val highlevel: Highlevel,
    val metadata: Metadata? = null
) {
    data class Highlevel(
        val danceability: Danceability? = null,
        val gender: Gender? = null,
        val genre_dortmund: GenreDortmund? = null,
        val genre_electronic: GenreElectronic? = null,
        val genre_rosamerica: GenreRosamerica? = null,
        val genre_tzanetakis: GenreTzanetakis? = null,
        val ismir04_rhythm: Ismir04Rhythm? = null,
        val mood_acoustic: MoodAcoustic? = null,
        val mood_aggressive: MoodAggressive? = null,
        val mood_electronic: MoodElectronic? = null,
        val mood_happy: MoodHappy? = null,
        val mood_party: MoodParty? = null,
        val mood_relaxed: MoodRelaxed? = null,
        val mood_sad: MoodSad? = null,
        val moods_mirex: MoodsMirex? = null,
        val timbre: Timbre? = null,
        val tonal_atonal: TonalAtonal? = null,
        val voice_instrumental: VoiceInstrumental? = null
    ) {
        data class Danceability(
            val all: All,
            val probability: Double,
            val value: String,
            val version: Version
        ) {
            data class All(
                val danceable: Double,
                @JSONField(name = "not_danceable")
                val notDanceable: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "ChaChaCha")
                val chaChaCha: Double,
                @JSONField(name = "Jive")
                val jive: Double,
                @JSONField(name = "Quickstep")
                val quickstep: Double,
                @JSONField(name = "Rumba-American")
                val rumbaAmerican: Double,
                @JSONField(name = "Rumba-International")
                val rumbaInternational: Double,
                @JSONField(name = "Rumba-Misc")
                val rumbaMisc: Double,
                @JSONField(name = "Samba")
                val samba: Double,
                @JSONField(name = "Tango")
                val tango: Double,
                @JSONField(name = "VienneseWaltz")
                val vienneseWaltz: Double,
                @JSONField(name = "Waltz")
                val waltz: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_acoustic")
                val notAcoustic: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_aggressive")
                val notAggressive: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_electronic")
                val notElectronic: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_happy")
                val notHappy: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_party")
                val notParty: Double,
                val party: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_relaxed")
                val notRelaxed: Double,
                val relaxed: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "not_sad")
                val notSad: Double,
                val sad: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "Cluster1")
                val cluster1: Double,
                @JSONField(name = "Cluster2")
                val cluster2: Double,
                @JSONField(name = "Cluster3")
                val cluster3: Double,
                @JSONField(name = "Cluster4")
                val cluster4: Double,
                @JSONField(name = "Cluster5")
                val cluster5: Double
            )

            data class Version(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
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
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )
        }
    }

    data class Metadata(
        @JSONField(name = "audio_properties")
        val audioProperties: AudioProperties,
        val tags: Tags,
        val version: Version
    ) {
        data class AudioProperties(
            @JSONField(name = "analysis_sample_rate")
            val analysisSampleRate: Int,
            @JSONField(name = "bit_rate")
            val bitRate: Int,
            val codec: String,
            val downmix: String,
            @JSONField(name = "equal_loudness")
            val equalLoudness: Int,
            val length: Double,
            val lossless: Int,
            @JSONField(name = "md5_encoded")
            val md5Encoded: String,
            @JSONField(name = "replay_gain")
            val replayGain: Double
        )

        data class Tags(
            @JSONField(name = "album")
            val album: List<String>? = null,
            @JSONField(name = "albumartist")
            val albumartist: List<String>? = null,
            @JSONField(name = "artist")
            val artist: List<String>? = null,
            @JSONField(name = "date")
            val date: List<String>? = null,
            @JSONField(name = "discnumber")
            val discnumber: List<String>? = null,
            @JSONField(name = "file_name")
            val fileName: String,
            @JSONField(name = "musicbrainz album type")
            val musicbrainzAlbumType: List<String>? = null,
            @JSONField(name = "musicbrainz_albumartistid")
            val musicbrainzAlbumartistid: List<String>? = null,
            @JSONField(name = "musicbrainz_albumid")
            val musicbrainzAlbumid: List<String>? = null,
            @JSONField(name = "musicbrainz_artistid")
            val musicbrainzArtistid: List<String>? = null,
            @JSONField(name = "musicbrainz_recordingid")
            val musicbrainzRecordingid: List<String>? = null,
            @JSONField(name = "musicbrainz release track id")
            val musicbrainzReleaseTrackId: List<String>? = null,
            @JSONField(name = "musicbrainz_releasegroupid")
            val musicbrainzReleasegroupid: List<String>? = null,
            val title: List<String>? = null,
            val tracknumber: List<String>? = null
        )

        data class Version(
            val highlevel: Highlevel,
            val lowlevel: Lowlevel
        ) {
            data class Highlevel(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String,
                val gaia: String,
                @JSONField(name = "gaia_git_sha")
                val gaiaGitSha: String,
                @JSONField(name = "models_essentia_git_sha")
                val modelsEssentiaGitSha: String
            )

            data class Lowlevel(
                val essentia: String,
                @JSONField(name = "essentia_build_sha")
                val essentiaBuildSha: String,
                @JSONField(name = "essentia_git_sha")
                val essentiaGitSha: String,
                val extractor: String
            )
        }
    }
}