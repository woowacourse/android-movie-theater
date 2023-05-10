package woowacourse.movie.database

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object SettingPreferencesManager : SharedPreferenceManager {
    private const val KEY_PUSH_ALARM = "KEY_PUSH_ALARM"
    private lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun changeData() {
        sharedPreferences.edit().putBoolean(KEY_PUSH_ALARM, !getData())
            .apply()
    }

    override fun getData(): Boolean {
        return sharedPreferences.getBoolean(KEY_PUSH_ALARM, false)
    }
}
