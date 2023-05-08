package woowacourse.movie.reservationList

import woowacourse.movie.common.model.ReservationsViewData

interface ReservationAdapterContract {
    interface View {
        val presenter: Presenter
        fun setAdapterData(reservations: ReservationsViewData)
    }

    interface Presenter {
        val view: View
        fun setReservation()
    }
}
