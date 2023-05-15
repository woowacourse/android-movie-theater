package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceUtil(context: Context) : SharedPreference {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    override fun setBoolean(key: String, boolean: Boolean) {
        prefs.edit().putBoolean(key, boolean).apply()
    }

    companion object {
        const val ALARM_KEY = "alarm_key"
    }
}
