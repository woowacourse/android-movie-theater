package woowacourse.movie.feature.setting

import woowacourse.movie.data.AlarmSettingRepository

class SettingPresenter(
    val view: SettingContract.View,
    private val settingRepository: AlarmSettingRepository
) : SettingContract.Presenter {
    override fun loadAlarmSettingInfo() {
        view.setInitAlarmSettingInfo(settingRepository.getEnablePushNotification())
    }

    override fun changeSwitchCheckedState(isPermissionApprove: Boolean, isSwitchChecked: Boolean) {
        if (!isPermissionApprove) view.alarmPermissionIsNotApprove()
        settingRepository.setEnablePushNotification(isSwitchChecked)
    }
}
