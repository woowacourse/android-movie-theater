package woowacourse.movie.domain.repository

import woowacourse.movie.data.repository.PreferenceRepository
import woowacourse.movie.data.source.NotificationPreference

class SharedPreferenceRepository(
    private val notificationPreference: NotificationPreference,
) : PreferenceRepository {
    override fun saveNotificationPreference(enabled: Boolean) {
        notificationPreference.saveNotificationPreference(enabled)
    }

    override fun loadNotificationPreference(): Boolean = notificationPreference.loadNotificationPreference()
}
