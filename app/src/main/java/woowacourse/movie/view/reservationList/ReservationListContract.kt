package woowacourse.movie.view.reservationList

import woowacourse.movie.model.MovieTicketModel

interface ReservationListContract {
    interface View {
        var presenter: Presenter

        fun setReservationView(movieTickets: List<MovieTicketModel>)

        fun updateReservationViewItem(itemSize: Int, diffSize: Int)
    }

    interface Presenter {
        fun setupReservations(movieTickets: List<MovieTicketModel>)

        fun loadReservations()

        fun setItemsInsertion()
    }
}
