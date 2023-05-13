package woowacourse.movie

import android.content.Context
import woowacourse.movie.BasePreference
import woowacourse.movie.main.MainActivity.Companion.SETTING_PREFERENCE_KEY

class SettingPreference(val context: Context) : BasePreference {

    private val sharedPreferences = context.getSharedPreferences(SETTING_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override fun setBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}
