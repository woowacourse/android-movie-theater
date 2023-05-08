package woowacourse.movie.view.moviemain.setting

import woowacourse.movie.view.model.ReservationUiModel

interface SettingContract {
    interface View {
        var presenter: Presenter
        fun setToggle(isOn: Boolean)
        fun cancelAlarms()
        fun setAlarms(reservations: List<ReservationUiModel>)
        fun requestNotificationPermission(): Boolean
    }

    interface Presenter {
        fun initState()
        fun changeAlarmState(isOn: Boolean)
    }
}
