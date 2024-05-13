package woowacourse.movie.setting

import woowacourse.movie.repository.SettingRepository

class SettingPresenter(
    private val view: SettingContract.View,
    private val settingRepository: SettingRepository,
    private val alarmInterface: AlarmInterface,
) : SettingContract.Presenter {
    override fun initSetting() {
        view.requestPermission()
        val checked = settingRepository.getAlarmState()
        view.showChecked(checked)
    }

    override fun toggleAlarm() {
        val alarmChecked = settingRepository.getAlarmState()
        if (alarmChecked) {
            settingRepository.setAlarmState(false)
            alarmInterface.deleteAllAlarms()
            view.showChecked(false)
        } else {
            settingRepository.setAlarmState(true)
            alarmInterface.makeAlarms()
            view.showChecked(true)
        }
    }
}
