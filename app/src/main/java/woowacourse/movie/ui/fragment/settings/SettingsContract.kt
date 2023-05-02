package woowacourse.movie.ui.fragment.settings

interface SettingsContract {
    interface Presentation {
        fun changePushNotificationState(enable: Boolean)

        fun getPushNotificationState(): Boolean
    }

    interface View {
        var presentation: Presentation
    }
}
