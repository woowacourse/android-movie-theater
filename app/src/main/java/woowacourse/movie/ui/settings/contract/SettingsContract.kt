package woowacourse.movie.ui.settings.contract

interface SettingsContract {
    interface Presenter {
        fun loadChecked(isChecked: Boolean)

        fun refreshChecked()
    }

    interface View {
        fun setSwitchChecked()
    }
}
