package woowacourse.movie.view.reservationcompleted

import woowacourse.movie.view.model.ReservationUiModel

interface ReservationCompletedContract {
    interface View {
        fun registerAlarm(reservation: ReservationUiModel)
    }
    interface Presenter {
        fun decideAlarm(reservation: ReservationUiModel)
        fun setAlarm(isOn: Boolean)
    }
}
