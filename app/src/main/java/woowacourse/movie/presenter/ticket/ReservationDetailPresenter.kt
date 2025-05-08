package woowacourse.movie.presenter.ticket

import woowacourse.movie.contract.ticket.ReservationDetailContract
import woowacourse.movie.domain.ticket.CancelTimePolicy
import woowacourse.movie.domain.ticket.DefaultCancelTimePolicy
import woowacourse.movie.domain.ticket.Reservation

class ReservationDetailPresenter(
    private val view: ReservationDetailContract.View,
    private val reservation: Reservation,
    private val cancelTimePolicy: CancelTimePolicy = DefaultCancelTimePolicy,
) : ReservationDetailContract.Presenter {
    override fun presentTitle() {
        view.setMovieTitle(reservation.title)
    }

    override fun presentShowtime() {
        view.setShowtime(reservation.showtime)
    }

    override fun presentCancelDescription() {
        view.setCancelDescription(cancelTimePolicy.cancelableMinutes)
    }

    override fun presentCount() {
        view.setCount(reservation.seats, reservation.cinemaName)
    }

    override fun presentPrice() {
        view.setPrice(reservation.price)
    }
}
