package woowacourse.movie.presentation.settings

interface SettingsContract {
    interface View {
        var presenter: Presenter
        fun initNotificationSwitch(isNotifiable: Boolean)
    }

    interface Presenter {
        fun setNotifiable(isNotifiable: Boolean)
    }
}
