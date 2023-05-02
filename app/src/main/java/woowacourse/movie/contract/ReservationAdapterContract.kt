package woowacourse.movie.contract

import woowacourse.movie.data.ReservationsViewData

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
