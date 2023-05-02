package woowacourse.movie.contract

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
