package woowacourse.movie.setting.view.contract

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun initSwitch(state: Boolean)
        fun bindSwitch()
    }

    interface Presenter {
        fun initState()
        fun getSwitchState(): Boolean
        fun setSwitchState(isChecked: Boolean)
    }
}
