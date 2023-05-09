package woowacourse.movie.dataSource

import android.content.Context
import android.content.SharedPreferences

class SharedSetting private constructor(private val sharedPreferences: SharedPreferences) {
    fun getValue(key: String, default: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    fun setValue(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit() ?: return
        editor.putBoolean(key, value).apply()
    }

    companion object {
        const val KEY = "shared"
        fun from(context: Context): SharedSetting {
            return SharedSetting(context.getSharedPreferences(KEY, Context.MODE_PRIVATE))
        }
    }
}
