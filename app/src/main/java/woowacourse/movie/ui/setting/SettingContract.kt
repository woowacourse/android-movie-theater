package woowacourse.movie.ui.setting

import woowacourse.movie.uimodel.MovieTicketModel

interface SettingContract {
    interface View {
        val presenter: Presenter

        fun setToggleButton(isChecked: Boolean)
        fun setAlarms(ticket: MovieTicketModel)
        fun cancelAlarms(ticket: MovieTicketModel)
    }

    interface Presenter {
        fun checkSwitchState()
        fun clickSwitch(isChecked: Boolean)
    }
}
