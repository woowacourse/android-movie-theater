package woowacourse.movie.setting

import woowacourse.movie.setting.uimodel.ReservationAlarmUiModel

interface SettingContract {
    interface View {
        fun turnOnAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>)

        fun turnOffAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>)

        fun showChecked(checked: Boolean)
    }

    interface Presenter {
        fun initSetting()

        fun toggleAlarm()
    }
}
