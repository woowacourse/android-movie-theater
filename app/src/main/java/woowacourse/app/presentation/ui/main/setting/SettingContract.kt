package woowacourse.app.presentation.ui.main.setting

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun setSwitch(isPushAlarmOn: Boolean)
    }

    interface Presenter {
        fun setPushAlarmSetting(isOn: Boolean)
        fun setSwitch()
    }
}
