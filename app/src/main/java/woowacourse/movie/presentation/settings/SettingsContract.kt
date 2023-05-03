package woowacourse.movie.presentation.settings

interface SettingsContract {
    interface View {
        val presenter: Presenter

        fun setSwitchSelectedState(state: Boolean)
    }

    interface Presenter {
        val view: View

        fun setNotificationSettings(notifyState: Boolean)
        fun getNotificationSettings()
    }
}
