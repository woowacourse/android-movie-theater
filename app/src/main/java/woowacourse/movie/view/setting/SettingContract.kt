package woowacourse.movie.view.setting

interface SettingContract {
    interface Presenter {
        fun initBinding(hasDeviceAlarmPermission: Boolean)

        fun notification(
            isChecked: Boolean,
            isDeviceAlarmPermission: Boolean,
            isNotificationPermissionGranted: Boolean,
        )
    }

    interface View {
        fun setCheckNotification(isCheck: Boolean)

        fun showPermissionNotificationDialog()

        fun showPermissionAlarmDialog()
    }
}
