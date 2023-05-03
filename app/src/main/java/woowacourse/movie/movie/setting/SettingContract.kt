package woowacourse.movie.movie.setting

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun initSwitch(state: Boolean)
        fun bindSwitch()
    }

    interface Presenter {
        fun initFragment()
        fun getSwitchState(): Boolean
        fun setSwitchState(isChecked: Boolean)
    }
}
