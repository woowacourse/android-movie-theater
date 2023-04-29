package woowacourse.movie.data.setting

import android.content.Context
import androidx.preference.PreferenceManager

class SettingPreferencesManager(context: Context) : SettingDataManager {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    override fun getIsAlarmSetting(): Boolean = sharedPreferences.getBoolean(IS_ALARM_ON, false)
    override fun setIsAlarmSetting(isOn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(IS_ALARM_ON, isOn).apply()
    }

    companion object {
        const val IS_ALARM_ON = "IS_ALARM_ON"
    }
}
