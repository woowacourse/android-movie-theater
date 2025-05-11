package woowacourse.movie.presentation.setting

interface SettingContract {
    interface View {
        fun showNotificationSetting(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadNotificationSetting()
        fun changeNotificationSetting(enabled: Boolean)
    }
}