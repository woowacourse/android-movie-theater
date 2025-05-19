package woowacourse.movie.view.setting

interface SettingContract {
    interface View {
        fun isNotificationPermitted(): Boolean

        fun attemptNotificationSettingChange(enabled: Boolean)

        fun showNotificationSetting(enabled: Boolean)
    }

    interface Presenter {
        fun loadSettings()

        fun toggleNotificationSetting()

        fun setNotificationSetting(enabled: Boolean)
    }
}
