package woowacourse.movie.model

import android.content.Context
import android.content.SharedPreferences
import woowacourse.movie.repository.SettingRepository

class PreferenceManager(private val context: Context) : SettingRepository {
    override fun setAlarmState(state: Boolean) {
        val sharedPreference =
            context.getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE,
            )
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putBoolean("notification", state).apply()
    }

    override fun getAlarmState(): Boolean {
        val sharedPreference =
            context.getSharedPreferences(
                "settings",
                Context.MODE_PRIVATE,
            )
        return sharedPreference.getBoolean("notification", false)
    }
}
