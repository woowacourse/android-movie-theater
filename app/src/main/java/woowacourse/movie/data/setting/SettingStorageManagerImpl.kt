package woowacourse.movie.data.setting

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit

class SettingStorageManagerImpl(context: Context) : SettingStorageManager {
    private val sharedPreferences = context.getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE)

    override fun isNotificationEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICATION, false)
    }

    override fun updateNotificationSetting(enabled: Boolean) {
        sharedPreferences.edit(commit = true) {
            putBoolean(KEY_NOTIFICATION, enabled)
        }
    }

    companion object {
        private const val KEY_SETTINGS = "settings"
        private const val KEY_NOTIFICATION = "notification_enabled"
    }
}
