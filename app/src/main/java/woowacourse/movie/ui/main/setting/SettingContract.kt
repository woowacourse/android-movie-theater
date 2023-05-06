package woowacourse.movie.ui.main.setting

interface SettingContract {
    interface View {
        var presenter: Presenter

        fun updateSwitch()
    }

    interface Presenter {
        fun getNotificationState(): Boolean

        fun setSwitchState(isChecked: Boolean)
    }
}
