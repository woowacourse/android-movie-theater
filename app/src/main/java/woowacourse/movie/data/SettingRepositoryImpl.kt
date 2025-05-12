package woowacourse.movie.data

import woowacourse.movie.data.preference.NotificationPreferenceManager
import woowacourse.movie.domain.SettingRepository

class SettingRepositoryImpl(
    private val preferenceManager: NotificationPreferenceManager = NotificationPreferenceManager(),
) : SettingRepository {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        preferenceManager.isNotificationEnabled = isEnabled
    }

    override fun isNotificationEnabled(): Boolean = preferenceManager.isNotificationEnabled
}
