package woowacourse.movie.model

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import woowacourse.movie.ui.main.SettingRepository

class SettingSharedPreference(context: Context) : SettingRepository {
    private val loginPreferences: SharedPreferences =
        context.getSharedPreferences(SETTING, Context.MODE_PRIVATE)

    override var state: Boolean
        get() = loginPreferences.getBoolean(PUSH_ALARM, false)
        set(value) = loginPreferences.edit { putBoolean(PUSH_ALARM, value) }

    companion object {
        private const val SETTING = "SETTING"
        private const val PUSH_ALARM = "PUSH_ALARM"
    }
}
