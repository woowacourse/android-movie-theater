package woowacourse.movie.ui.fragment.settings

interface SettingsContract {
    interface Presenter {
        fun changePushNotificationState(enable: Boolean)

        fun checkPushNotificationState()
    }

    interface View {
        var presenter: Presenter

        fun setPushNotificationState(enable: Boolean)
    }
}
