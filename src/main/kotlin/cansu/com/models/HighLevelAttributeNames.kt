package cansu.com.models


enum class AttributeNameEnums {
    danceability,
    gender,
    genre_dortmund,
    genre_electronic,
    genre_rosamerica,
    genre_tzanetakis,
    ismir04_rhythm,
    mood_rhythm,
    mood_acoustic,
    mood_aggressive,
    mood_electronic,
    mood_happy,
    mood_party,
    mood_relaxed,
    mood_sad,
    moods_mirex,
    voice_instrumental,
    timbre,
    tonal_atonal;

    companion object {
        fun CreatePSQLTypeIfNotExists(): String {
            // DO $$ BEGIN
            //    CREATE TYPE my_type AS (/* fields go here */);
            //EXCEPTION
            //    WHEN duplicate_object THEN null;
            //END $$;
            // to replicate CREATE TYPE IF NOT EXISTS
            // https://stackoverflow.com/a/48382296/22757599
            var baseline_query: String = "DO \$\$ BEGIN \n\t CREATE TYPE AttributeNames AS ENUM ("
            AttributeNameEnums.entries.forEach { entry -> baseline_query = baseline_query + "'" + entry.name + "'," }
            baseline_query = "${baseline_query.dropLast(1)});"
            baseline_query = "$baseline_query\n EXCEPTION\n \tWHEN duplicate_object THEN null; \nEND \$\$;"
            return baseline_query
        }
    }
}