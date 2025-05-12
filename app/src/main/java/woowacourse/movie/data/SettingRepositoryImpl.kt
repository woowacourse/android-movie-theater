package woowacourse.movie.data

import woowacourse.movie.data.preference.NotificationPreferenceManager
import woowacourse.movie.domain.SettingRepository

class SettingRepositoryImpl(
    private val preferenceManager: NotificationPreferenceManager
) : SettingRepository {
    override fun updateNotificationEnabled(isEnabled: Boolean) {
        preferenceManager.updateNotificationEnabled(isEnabled)
    }

    override fun isNotificationEnabled(): Boolean = preferenceManager.isNotificationEnabled()
}
