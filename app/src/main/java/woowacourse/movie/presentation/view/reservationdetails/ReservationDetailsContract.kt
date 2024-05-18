package woowacourse.movie.presentation.view.reservationdetails

import woowacourse.movie.presentation.uimodel.MovieTicketUiModel

interface ReservationDetailsContract {
    interface View {
        fun showDetailsList(ticketList: List<MovieTicketUiModel>)

        fun moveToReservationResult(ticketUiModel: MovieTicketUiModel)
    }

    interface Presenter {
        fun loadDetailsList()

        fun onDetailItemClicked(ticketId: Long)
    }

    interface ViewActions {
        fun onDetailItemClicked(ticketId: Long)
    }
}
