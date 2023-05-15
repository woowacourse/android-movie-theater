package woowacourse.app.presentation.ui.main.setting

import woowacourse.app.data.pushAlarm.PushAlarmDataSource

class SettingPresenter(
    private val view: SettingContract.View,
    private val pushAlarmDataSource: PushAlarmDataSource,
) : SettingContract.Presenter {

    init {
        setSwitch()
    }

    override fun setPushAlarmSetting(isOn: Boolean) {
        if (isOn) {
            pushAlarmDataSource.setPushAlarmOn()
            return
        }
        pushAlarmDataSource.setPushAlarmOff()
    }

    override fun setSwitch() {
        view.setSwitch(pushAlarmDataSource.getPushAlarmState())
    }
}
