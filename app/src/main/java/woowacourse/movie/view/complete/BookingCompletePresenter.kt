package woowacourse.movie.view.complete

import android.content.Context
import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.db.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.model.datasource.TicketDataSource
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val dataSource: TicketDataSource,
) : BookingCompleteContract.Presenter {
    override fun loadTicket(
        ticketId: Long,
        requestAlarm: Boolean,
    ) {
        thread {
            val ticket = dataSource.getTicketById(ticketId)
            Handler(Looper.getMainLooper()).post {
                view.showTicket(ticket)
                if (requestAlarm) view.generateAlarm(ticket)
            }
        }
    }

    companion object {
        fun initialize(
            view: BookingCompleteContract.View,
            context: Context,
        ): BookingCompleteContract.Presenter {
            val dao = UserDatabase.getDatabase(context).ticketDao()
            val dataSource = TicketDataSourceImpl(dao)
            return BookingCompletePresenter(view, dataSource)
        }
    }
}
