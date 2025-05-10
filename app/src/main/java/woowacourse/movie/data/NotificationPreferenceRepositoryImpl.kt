package woowacourse.movie.data

import woowacourse.movie.data.preference.NotificationPreferenceManager
import woowacourse.movie.domain.NotificationPreferenceRepository

class NotificationPreferenceRepositoryImpl(
    private val notificationPreferenceManager: NotificationPreferenceManager,
) : NotificationPreferenceRepository {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        notificationPreferenceManager.isNotificationEnabled = isEnabled
    }

    override fun notificationEnabled(): Boolean = notificationPreferenceManager.isNotificationEnabled
}
