package woowacourse.movie.presentation.util

import woowacourse.movie.data.SettingsData

object SettingsNotificationData {
    private const val SETTINGS_NOTIFICATION_KEY = "notification"

    fun setNotification(notifyState: Boolean) {
        SettingsData.setBooleanData(SETTINGS_NOTIFICATION_KEY, notifyState)
    }

    fun getNotification(): Boolean =
        SettingsData.getBooleanData(SETTINGS_NOTIFICATION_KEY)
}
