package woowacourse.movie.view.main.setting

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun setSwitchState(isChecked: Boolean)
    }

    interface Presenter {
        fun onClickSwitch()
        fun updateSwitchState()
    }
}
