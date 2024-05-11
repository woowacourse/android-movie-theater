package woowacourse.movie.setting

import woowacourse.movie.setting.uimodel.ReservationAlarmUiModel

interface SettingContract {
    interface View {
        fun turnOnAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>)

        fun turnOffAlarm(reservationAlarmUiModels: List<ReservationAlarmUiModel>)
    }

    interface Presenter {
        fun initSetting(checked: Boolean)

        fun toggleAlarm()
    }
}
