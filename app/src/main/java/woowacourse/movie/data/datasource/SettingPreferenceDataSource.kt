package woowacourse.movie.data.datasource

import android.content.Context
import androidx.core.content.edit

class SettingPreferenceDataSource(
    val context: Context,
) {
    private val sharedPreference = context.getSharedPreferences(NOTIFICATION_SETTING_KEY, Context.MODE_PRIVATE)

    fun getNotificationSetting(): Boolean = sharedPreference.getBoolean(NOTIFICATION_SETTING_KEY, true)

    fun putNotificationSetting(isNotificationEnabled: Boolean) {
        sharedPreference.edit { putBoolean(NOTIFICATION_SETTING_KEY, isNotificationEnabled) }
    }

    companion object {
        private const val NOTIFICATION_SETTING_KEY = "NOTIFICATION_SETTING"
    }
}
