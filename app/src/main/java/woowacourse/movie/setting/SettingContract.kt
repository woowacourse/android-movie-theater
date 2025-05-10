package woowacourse.movie.setting

interface SettingContract {
    interface View {
        fun showAlarmState()
    }

    interface Presenter {
        fun setNotificationAlarm(isChecked: Boolean)

        fun checkAllPermission()
    }
}
