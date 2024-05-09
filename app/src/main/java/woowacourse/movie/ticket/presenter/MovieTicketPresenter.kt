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
        view.showTicketView(TicketDataResource.dbTicket)
    }
}
