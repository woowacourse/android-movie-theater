package woowacourse.movie.data

import android.content.SharedPreferences
import androidx.core.content.edit

class NotificationRepositoryImpl(
    private val preferences: SharedPreferences,
) : NotificationRepository {
    override fun setNotificationEnabled(enabled: Boolean) {
        preferences.edit { putBoolean("notification_isEnabled", enabled) }
    }

    override fun getNotificationEnabled(): Boolean {
        return preferences.getBoolean("notification_isEnabled", false)
    }
}
