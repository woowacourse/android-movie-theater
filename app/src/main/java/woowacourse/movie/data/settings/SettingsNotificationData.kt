package woowacourse.movie.data.settings

object SettingsNotificationData {
    private const val SETTINGS_NOTIFICATION_KEY = "notification"

    fun setNotification(notifyState: Boolean) {
        SettingsDataAdapter(SettingsPreference).setBooleanData(
            SETTINGS_NOTIFICATION_KEY,
            notifyState,
        )
    }

    fun getNotification(): Boolean =
        SettingsDataAdapter(SettingsPreference).getBooleanData(SETTINGS_NOTIFICATION_KEY, true)
}
