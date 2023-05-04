package woowacourse.movie.data.dataSource

import android.content.Context
import android.content.SharedPreferences

object SharedSetting {
    private const val KEY = "shared"
    private var sharedPreference: SharedPreferences? = null

    fun initWithContext(context: Context) {
        context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
    }

    fun getValue(key: String, default: Boolean = false): Boolean {
        return sharedPreference?.getBoolean(key, default) ?: default
    }

    fun setValue(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreference?.edit() ?: return
        editor.putBoolean(key, value).apply()
    }
}
