package woowacourse.movie.model.data.local

import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object SerializableDAO {
    inline fun <reified T> addToList(
        serializable: T,
        preference: SharedPreferences,
        preferenceKey: String
    ) {
        val json: String =
            preference.getString(preferenceKey, null) ?: Json.encodeToString(emptyList<T>())
        val oldData: List<T> = jsonConvertToSerializable(json)
        val newData: List<T> =
            oldData + serializable
        preference.edit().putString(preferenceKey, serializableConvertToJson(newData)).apply()
    }

    inline fun <reified T> get(preference: SharedPreferences, preferenceKey: String): T? {
        val json: String? = preference.getString(preferenceKey, null)

        if (json == null) return json
        return jsonConvertToSerializable(json)
    }

    inline fun <reified T> jsonConvertToSerializable(json: String): T =
        Json.decodeFromString(json)

    inline fun <reified T> serializableConvertToJson(serializable: T): String =
        Json.encodeToString(serializable)
}
