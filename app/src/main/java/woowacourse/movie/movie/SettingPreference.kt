package woowacourse.movie.movie

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.movie.MainActivity.Companion.SETTING_PREFERENCE_KEY

object SettingPreference : BasePreference {

    lateinit var sharedPreferences: SharedPreferences

    fun initSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(SETTING_PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    override fun setBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }
}
