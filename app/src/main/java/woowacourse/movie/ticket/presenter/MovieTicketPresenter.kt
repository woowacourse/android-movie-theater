package woowacourse.movie.ticket.presenter

import android.content.Context
import woowacourse.movie.database.TicketDatabase
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.TicketDataResource
import woowacourse.movie.ticket.model.TicketEntity
import java.io.Serializable
import kotlin.concurrent.thread

class MovieTicketPresenter(
    val view: MovieTicketContract.View,
) : MovieTicketContract.Presenter {

    override fun storeTicketData(ticket: Serializable?, movieId: Long?) {
        val ticketDb = TicketDatabase.getDatabase(view as Context)
        if (ticket == null) {
            var ticketEntities: List<TicketEntity>? = null
            thread {
                ticketEntities = ticketDb.ticketDao().getAll()
            }.join()
            view.showTicketView(ticketEntities!!.first { it.id == movieId })
            return
        }
        thread {
            ticketDb.ticketDao().insertAll(ticket as TicketEntity)
            view.makeAlarm(ticketDb.ticketDao().getLast())
            view.showTicketView(ticketDb.ticketDao().getLast())
        }.join()
    }

    override fun setTicketInfo() {
        view.showTicketView(TicketDataResource.ticketEntity)
    }
}
