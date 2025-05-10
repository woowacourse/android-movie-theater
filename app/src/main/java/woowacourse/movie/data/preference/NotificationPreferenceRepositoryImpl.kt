package woowacourse.movie.data.preference

import woowacourse.movie.domain.NotificationPreferenceRepository

class NotificationPreferenceRepositoryImpl(
    private val preferenceManager: PreferenceManager,
) : NotificationPreferenceRepository {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        preferenceManager.isNotificationEnabled = isEnabled
    }

    override fun notificationEnabled(): Boolean = preferenceManager.isNotificationEnabled
}
