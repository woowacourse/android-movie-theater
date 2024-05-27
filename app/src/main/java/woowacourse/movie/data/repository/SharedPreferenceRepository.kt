package woowacourse.movie.data.repository

import woowacourse.movie.preference.NotificationPreference

class SharedPreferenceRepository(
    private val notificationPreference: NotificationPreference
) : PreferenceRepository {
    override fun saveNotificationPreference(enabled: Boolean) {
        notificationPreference.saveNotificationPreference(enabled)
    }

    override fun loadNotificationPreference(): Boolean = notificationPreference.loadNotificationPreference()
}

