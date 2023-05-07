package woowacourse.movie.presentation.settings

interface SettingsContract {
    interface View {
        var presenter: Presenter
        fun initNotificationSwitch(isNotifiable: Boolean)
    }

    interface Presenter {
        var isNotifiable: Boolean

        fun initNotifiable()
    }
}
