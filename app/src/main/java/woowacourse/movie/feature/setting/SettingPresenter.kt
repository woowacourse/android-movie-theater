package woowacourse.movie.feature.setting

import woowacourse.movie.data.AlarmSettingRepository

class SettingPresenter(
    val view: SettingContract.View,
    val settingRepository: AlarmSettingRepository
) : SettingContract.Presenter {
    override fun loadAlarmSettingInfo() {
        view.setInitAlarmSettingInfo(settingRepository.enablePushNotification)
    }

    override fun changeSwitchCheckedState(isPermissionApprove: Boolean, isSwitchChecked: Boolean) {
        if (!isPermissionApprove) view.alarmPermissionIsNotApprove()
        settingRepository.enablePushNotification = isSwitchChecked
    }
}
