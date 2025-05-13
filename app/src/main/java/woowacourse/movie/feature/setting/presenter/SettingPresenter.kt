package woowacourse.movie.feature.setting.presenter

import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.feature.setting.contract.SettingContract

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    override fun loadNotificationSetting() {
        val isNotificationEnabled = settingRepository.fetchNotificationSetting()
        view.updateNotificationSettingSwitch(isNotificationEnabled)
    }

    override fun saveNotificationSetting(isNotificationEnabled: Boolean) {
        settingRepository.saveNotificationSetting(isNotificationEnabled)
    }
}
