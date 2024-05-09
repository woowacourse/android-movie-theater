package woowacourse.movie.ticket.presenter

import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.model.TicketDataResource
import java.io.Serializable

class MovieTicketPresenter(
    val view: MovieTicketContract.View,
) : MovieTicketContract.Presenter {

    override fun storeTicketData(ticket: Serializable?) {
        TicketDataResource.dbTicket = ticket as DbTicket
    }

    override fun storeTicketInDb() {
        view.storeTicketInDb(TicketDataResource.dbTicket)
    }

    override fun setTicketInfo() {
        view.showTicketView(
            TicketDataResource.dbTicket.movieTitle,
            TicketDataResource.dbTicket.screeningDate,
            TicketDataResource.dbTicket.screeningTime,
            TicketDataResource.dbTicket.seatsCount,
            TicketDataResource.dbTicket.seats,
            TicketDataResource.dbTicket.theaterName,
            TicketDataResource.dbTicket.price,
        )
    }
}
