package woowacourse.movie.ui.fragment.settings

interface SettingsContract {
    interface Presentation {
        fun changePushNotificationState(enable: Boolean)

        fun checkPushNotificationState()
    }

    interface View {
        var presentation: Presentation

        fun setPushNotificationState(enable: Boolean)
    }
}
