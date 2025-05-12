package woowacourse.movie.presentation.view.setting

interface SettingContract {
    interface View {
        fun updateSwitch(isEnabled: Boolean)
    }

    interface Presenter {
        fun checkPreference()

        fun updatePreference(enabled: Boolean)
    }
}
