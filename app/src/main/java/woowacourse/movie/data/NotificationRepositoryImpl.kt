package woowacourse.movie.data

import woowacourse.movie.GlobalApplication
import woowacourse.movie.data.preference.NotificationPreferenceManager
import woowacourse.movie.domain.NotificationPreferenceRepository

class NotificationRepositoryImpl(
    private val preferenceManager: NotificationPreferenceManager =
        NotificationPreferenceManager.getInstance(
            GlobalApplication.instance,
        ),
) : NotificationPreferenceRepository {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        preferenceManager.isNotificationEnabled = isEnabled
    }

    override fun notificationEnabled(): Boolean = preferenceManager.isNotificationEnabled
}
