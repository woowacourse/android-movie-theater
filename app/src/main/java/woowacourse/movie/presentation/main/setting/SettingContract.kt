package woowacourse.movie.presentation.main.setting

interface SettingContract {
    interface View {
        fun requestNotificationPermission()
        fun updateAllNotifications()
        fun cancelAllNotifications()
        fun showMessage(message: String)
        fun setAlarmSwitchChecked(isChecked: Boolean)
    }

    interface Presenter {
        fun onAlarmSwitchChanged(isChecked: Boolean)
        fun onPermissionResult(isGranted: Boolean)
        fun initializeSettings()
        fun updateAllNotifications()
        fun cancelAllNotifications()
    }
}
