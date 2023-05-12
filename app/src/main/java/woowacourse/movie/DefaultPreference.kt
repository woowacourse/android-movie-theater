package woowacourse.movie

import android.content.Context
import android.content.SharedPreferences

class DefaultPreference(context: Context) : SharedPreference {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            "prefs_name",
            Context.MODE_PRIVATE + Context.MODE_MULTI_PROCESS
        )

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }
}
