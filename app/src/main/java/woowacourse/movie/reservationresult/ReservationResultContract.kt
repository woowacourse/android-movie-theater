package woowacourse.movie.reservationresult

import woowacourse.movie.reservationresult.uimodel.ReservationResultUiModel

interface ReservationResultContract {
    interface View {
        fun showResult(reservationResult: ReservationResultUiModel)
    }

    interface Presenter {
        fun loadReservationResult(reservationId: Long)
    }
}
