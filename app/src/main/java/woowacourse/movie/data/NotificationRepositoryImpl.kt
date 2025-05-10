package woowacourse.movie.data

import woowacourse.movie.data.preference.NotificationPreferenceManager
import woowacourse.movie.domain.NotificationRepository

class NotificationRepositoryImpl(
    private val preferenceManager: NotificationPreferenceManager = NotificationPreferenceManager(),
) : NotificationRepository {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        preferenceManager.isNotificationEnabled = isEnabled
    }

    override fun notificationEnabled(): Boolean = preferenceManager.isNotificationEnabled
}
