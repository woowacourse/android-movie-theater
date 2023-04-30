package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun setBoolean(key: String, boolean: Boolean) {
        prefs.edit().putBoolean(key, boolean).apply()
    }
}
