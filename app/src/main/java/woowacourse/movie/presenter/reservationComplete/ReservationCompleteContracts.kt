package woowacourse.movie.presenter.reservationComplete

import woowacourse.movie.data.entity.MovieTicketEntity

interface ReservationCompleteContracts {
    interface View {
        fun showMovieTicket(movieTicketEntity: MovieTicketEntity)
    }

    interface Presenter {
        fun fetchTicketData(reservationDetailId: Long)
    }
}
