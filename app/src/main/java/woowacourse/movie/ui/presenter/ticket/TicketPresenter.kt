package woowacourse.movie.ui.presenter.ticket

import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.CancelTimePolicy
import woowacourse.movie.domain.ticket.DefaultCancelTimePolicy
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.contract.ticket.TicketContract

class TicketPresenter(
    private val view: woowacourse.movie.ui.contract.ticket.TicketContract.View,
    private val ticket: Ticket,
    private val seats: Set<Seat>,
    private val cinemaName: String,
    private val cancelTimePolicy: CancelTimePolicy = DefaultCancelTimePolicy,
) : woowacourse.movie.ui.contract.ticket.TicketContract.Presenter {
    override fun presentTitle() {
        view.setMovieTitle(ticket.title)
    }

    override fun presentShowtime() {
        view.setShowtime(ticket.showtime)
    }

    override fun presentCancelDescription() {
        view.setCancelDescription(cancelTimePolicy.cancelableMinutes)
    }

    override fun presentCount() {
        view.setCount(ticket.count, seats, cinemaName)
    }

    override fun presentPrice() {
        view.setPrice(ticket.price)
    }
}
