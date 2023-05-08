package woowacourse.movie.view.moviemain.setting

import woowacourse.movie.view.model.ReservationUiModel

interface SettingContract {
    interface View {
        val presenter: Presenter
        fun initToggleState(isAlarmOn: Boolean)
        fun setToggleState(isAlarmOn: Boolean)
        fun resetAlarms(reservations: List<ReservationUiModel>, timeInterval: Long)
    }

    interface Presenter {
        fun loadAlarmSetting()
        fun setAlarmPreference(isAlarmOn: Boolean)
        fun resetAlarms()
    }
}
