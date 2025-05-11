package woowacourse.movie.presentation.view.setting

import woowacourse.movie.domain.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository,
) : SettingContract.Presenter {
    override fun fetchSettingInfo() {
        val isEnabled = settingRepository.getNotificationEnabled()
        view.showPushAlarmSetting(isEnabled)
    }

    override fun savePushAlarmSetting(isEnabled: Boolean) {
        settingRepository.setNotificationEnabled(isEnabled)
        view.showPushAlarmSetting(isEnabled)
    }
}
