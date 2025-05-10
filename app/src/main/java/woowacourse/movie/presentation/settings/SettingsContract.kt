package woowacourse.movie.presentation.settings

interface SettingsContract {
    interface View {
        fun updateNotificationSetting(isChecked: Boolean)
    }

    interface Presenter {
        fun loadSettings()

        fun saveNotificationSetting(isEnabled: Boolean)
    }
}
