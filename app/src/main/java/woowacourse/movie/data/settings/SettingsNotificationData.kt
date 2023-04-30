package woowacourse.movie.data.settings

object SettingsNotificationData {
    private const val SETTINGS_NOTIFICATION_KEY = "notification"
    private val settingsData = SettingsDataAdapter(SettingsPreference)

    fun setNotification(notifyState: Boolean) {
        settingsData.setBooleanData(SETTINGS_NOTIFICATION_KEY, notifyState)
    }

    fun getNotification(): Boolean =
        settingsData.getBooleanData(SETTINGS_NOTIFICATION_KEY, true)
}
