package woowacourse.movie.presentation.settings

interface SettingsContract {
    interface View {
        var presenter: Presenter
        fun initNotifiable(isNotifiable: Boolean)
    }

    interface Presenter {
        var isNotifiable: Boolean

        fun initNotifiable()
    }
}
