package woowacourse.movie.data.repository

import woowacourse.movie.data.datasource.SettingPreferenceDataSource
import woowacourse.movie.domain.repository.SettingRepository

class SettingRepositoryImpl(
    private val settingDataSource: SettingPreferenceDataSource,
) : SettingRepository {
    override fun fetchNotificationSetting(): Boolean = settingDataSource.getNotificationSetting()

    override fun saveNotificationSetting(isNotificationEnabled: Boolean) {
        settingDataSource.putNotificationSetting(isNotificationEnabled)
    }
}
