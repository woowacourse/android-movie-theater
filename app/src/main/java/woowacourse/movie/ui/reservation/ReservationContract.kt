package woowacourse.movie.ui.reservation

import woowacourse.movie.uimodel.MovieTicketModel

class ReservationContract {
    interface View {
        val presenter: Presenter

        fun setReservationViewAdapter(tickets: List<MovieTicketModel>)
        fun clickItem(ticket: MovieTicketModel)
        fun setEmptyStateText(isEmpty: Boolean)
    }

    interface Presenter {
        fun initAdapter()
        fun clickItem(position: Int)
        fun checkDataExisting()
    }
}
