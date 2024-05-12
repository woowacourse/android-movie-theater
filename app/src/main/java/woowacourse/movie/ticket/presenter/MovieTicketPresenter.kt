package woowacourse.movie.ticket.presenter

import android.content.Context
import android.util.Log
import woowacourse.movie.database.TicketDatabase
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.model.TicketDataResource
import java.io.Serializable
import kotlin.concurrent.thread

class MovieTicketPresenter(
    val view: MovieTicketContract.View,
) : MovieTicketContract.Presenter {

    override fun storeTicketData(ticket: Serializable?, movieId: Long?) {
        val ticketDb = TicketDatabase.getDatabase(view as Context)
        if (ticket == null) {
            var dbTickets: List<DbTicket>? = null
            thread {
                dbTickets = ticketDb.ticketDao().getAll()
            }.join()
            view.showTicketView(dbTickets!!.first { it.id == movieId })
            Log.d("alsong", "storeTicketData ${dbTickets!!.first { it.id == movieId }}")
            return
        }
        TicketDataResource.dbTicket = ticket as DbTicket
        Thread {
            ticketDb.ticketDao().insertAll(ticket)
        }.start()
        view.makeAlarm(TicketDataResource.dbTicket)
    }

    override fun setTicketInfo() {
        view.showTicketView(TicketDataResource.dbTicket)
    }
}
