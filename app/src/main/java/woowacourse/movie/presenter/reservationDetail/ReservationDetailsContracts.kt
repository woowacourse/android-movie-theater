package woowacourse.movie.presenter.reservationDetail

import android.content.Context
import woowacourse.movie.data.entity.MovieTicketEntity

interface ReservationDetailsContracts {
    interface View {
        fun showReservationDetails(reservationDetails: List<MovieTicketEntity>)
    }

    interface Presenter {
        fun updateReservationDetails(context: Context)
    }
}
