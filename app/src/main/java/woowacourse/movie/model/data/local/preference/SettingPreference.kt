package woowacourse.movie.model.data.local.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import woowacourse.movie.model.data.storage.SettingStorage

class SettingPreference(context: Context) : SettingStorage {
    private val value by lazy {
        context.getSharedPreferences(SETTINGS, MODE_PRIVATE)
    }

    override fun setNotificationSettings(notifyState: Boolean): Boolean =
        value.edit().putBoolean(SETTINGS_NOTIFICATION, notifyState).commit()

    override fun getNotificationSettings(): Boolean = value.getBoolean(SETTINGS_NOTIFICATION, true)

    companion object {
        private const val SETTINGS = "settings"

        // 세부 항목
        private val SETTINGS_NOTIFICATION = "notification"
    }
}
