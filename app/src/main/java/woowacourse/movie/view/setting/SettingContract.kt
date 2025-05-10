package woowacourse.movie.view.setting

interface SettingContract {
    interface View {
        fun showNotificationSetting(notificationEnabled: Boolean)
    }

    interface Presenter {
        fun loadSettings()

        fun toggleNotificationSetting()
    }
}
