package woowacourse.movie.presentation.util

import woowacourse.movie.MovieTheaterApplication

object SharedPreferenceUtil {
    private val SETTINGS_NOTIFICATION = "notification"

    fun setNotificationSettings(notifyState: Boolean) {
        MovieTheaterApplication.settingsPreference.edit()
            .putBoolean(SETTINGS_NOTIFICATION, notifyState).apply()
    }

    fun getNotificationSettings(): Boolean =
        MovieTheaterApplication.settingsPreference.getBoolean(SETTINGS_NOTIFICATION, true)
}
