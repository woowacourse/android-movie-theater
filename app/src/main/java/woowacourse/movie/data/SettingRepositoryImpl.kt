package woowacourse.movie.data

import woowacourse.movie.domain.SettingRepository

class SettingRepositoryImpl(
    private val preferenceManager: SettingPreferenceManager,
) : SettingRepository {
    override fun getNotificationEnabled() = preferenceManager.getNotificationEnabled()

    override fun setNotificationEnabled(enabled: Boolean) {
        preferenceManager.setNotificationEnabled(enabled)
    }
}
