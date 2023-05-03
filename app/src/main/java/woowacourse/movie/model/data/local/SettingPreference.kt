package woowacourse.movie.model.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import woowacourse.movie.model.data.storage.SettingStorage

class SettingPreference(context: Context) : SettingStorage {
    private val value by lazy {
        context.getSharedPreferences(SETTINGS, MODE_PRIVATE)
    }

    override fun setNotificationSettings(notifyState: Boolean) {
        value.edit().putBoolean(SETTINGS_NOTIFICATION, notifyState).apply()
    }

    override fun getNotificationSettings(): Boolean = value.getBoolean(SETTINGS_NOTIFICATION, true)

    companion object {
        private const val SETTINGS = "settings"

        // 세부 항목
        private val SETTINGS_NOTIFICATION = "notification"
    }
}
