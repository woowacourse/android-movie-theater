package woowacourse.movie.ui.fragment.reservationlist

import woowacourse.movie.ui.model.MovieTicketModel

interface ReservationListContract {
    interface Presenter {
        fun setUpReservations()

        fun setReservations()

        fun checkItemInsertion(count: Int)
    }

    interface View {
        var presenter: Presenter

        fun setReservations(reservations: List<MovieTicketModel>)

        fun notifyReservationInsertion(count: Int)
    }
}
