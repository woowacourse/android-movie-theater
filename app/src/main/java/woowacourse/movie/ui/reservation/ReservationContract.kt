package woowacourse.movie.ui.reservation

import woowacourse.movie.uimodel.MovieTicketModel

class ReservationContract {
    interface View {
        val presenter: Presenter

        fun setReservationViewAdapter(tickets: List<MovieTicketModel>)
        fun clickItem(ticket: MovieTicketModel)
    }

    interface Presenter {
        fun setAdapter()
        fun clickItem(position: Int)
        fun isReservationEmpty(): Boolean
    }
}
