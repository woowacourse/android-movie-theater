package woowacourse.movie.presenter.reservationDetail

import woowacourse.movie.data.entity.MovieTicketEntity

interface ReservationDetailsContracts {
    interface View {
        fun showReservationDetails(reservationDetails: List<MovieTicketEntity>)
    }

    interface Presenter {
        fun fetchReservationDetails()
    }
}
