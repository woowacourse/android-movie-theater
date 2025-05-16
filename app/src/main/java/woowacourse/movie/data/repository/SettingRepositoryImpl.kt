package woowacourse.movie.data.repository

import woowacourse.movie.data.SettingPreferenceManager
import woowacourse.movie.domain.repository.SettingRepository

class SettingRepositoryImpl(
    private val preferenceManager: SettingPreferenceManager,
) : SettingRepository {
    override fun getNotificationEnabled() = preferenceManager.getNotificationEnabled()

    override fun setNotificationEnabled(enabled: Boolean) {
        preferenceManager.setNotificationEnabled(enabled)
    }
}
