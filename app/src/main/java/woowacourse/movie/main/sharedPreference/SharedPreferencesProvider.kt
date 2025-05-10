package woowacourse.movie.main.sharedPreference

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesProvider(
    context: Context,
) : PreferencesProvider {
    private val sharedPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun isAlarmEnabled(): Boolean {
        return sharedPref.getBoolean("notification", true)
    }

    override fun setAlarmEnabled(enabled: Boolean) {
        sharedPref.edit { putBoolean("notification", enabled) }
    }
}
