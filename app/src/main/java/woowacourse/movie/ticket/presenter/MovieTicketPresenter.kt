package woowacourse.movie.ticket.presenter

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.Ticket
import woowacourse.movie.ticket.model.TicketDataResource

class MovieTicketPresenter(
    val view: MovieTicketContract.View,
) : MovieTicketContract.Presenter {

    override fun storeTicketData(
        theaterId: Long,
        ticketCount: Count,
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        price: Int,
        seats: List<Seat>
    ) {
        TicketDataResource.ticket =
            Ticket(price, seats, ticketCount, screeningDate, screeningTime, movieId, theaterId)
    }

    override fun setTicketInfo() {
        view.showTicketView(
            TicketDataResource.ticket.title,
            TicketDataResource.ticket.price,
            TicketDataResource.ticket.seats.size,
            TicketDataResource.ticket.seats,
            TicketDataResource.ticket.theater,
            TicketDataResource.ticket.screeningDate,
            TicketDataResource.ticket.screeningTime,
        )
    }
}
