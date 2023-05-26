package woowacourse.movie.ui.main.setting

interface SettingContract {
    interface View {
        var presenter: Presenter

        fun updateSwitch(pushAlarmState: Boolean)
    }

    interface Presenter {
        fun getNotificationState()

        fun setSwitchState(isChecked: Boolean)
    }
}
