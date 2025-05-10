package woowacourse.movie.feature.setting

interface SettingContract {
    interface View {
        fun setNotificationSwitchChecked(isChecked: Boolean)

        fun showNotificationPermissionRequest()
    }

    interface Presenter {
        fun loadNotificationSettings()

        fun toggleNotificationSwitch(isChecked: Boolean)
    }
}
