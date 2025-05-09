package woowacourse.movie.view.complete

import android.content.Context
import woowacourse.movie.data.db.TicketDataSource
import woowacourse.movie.data.db.UserDatabase
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val dataSource: TicketDataSource,
) : BookingCompleteContract.Presenter {
    override fun loadTicket(ticketId: Long) {
        thread {
            val ticket = dataSource.getTicketById(ticketId)
            view.showTicket(ticket)
        }
    }

    companion object {
        fun initialize(
            view: BookingCompleteContract.View,
            context: Context,
        ): BookingCompleteContract.Presenter {
            val dao = UserDatabase.getDatabase(context).ticketDao()
            val dataSource = TicketDataSource(dao)
            return BookingCompletePresenter(view, dataSource)
        }
    }
}
