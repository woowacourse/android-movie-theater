package woowacourse.movie.ui.settings.contract

interface SettingsContract {
    interface Presenter {
        fun loadSwitchChecked(isNotificationOptionChecked: Boolean)

        fun refreshChecked()
    }

    interface View {
        fun setSwitchChecked(isNotificationOptionChecked: Boolean)
    }
}
