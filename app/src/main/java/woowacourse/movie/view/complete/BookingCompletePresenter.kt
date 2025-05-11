package woowacourse.movie.view.complete

import android.content.Context
import woowacourse.movie.data.db.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.model.datasource.TicketDataSource
import woowacourse.movie.view.core.util.DefaultMainThreadExecutor
import woowacourse.movie.view.core.util.MainThreadExecutor
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val dataSource: TicketDataSource,
    private val mainThreadExecutor: MainThreadExecutor,
) : BookingCompleteContract.Presenter {
    override fun loadTicket(
        ticketId: Long,
        requestAlarm: Boolean,
    ) {
        thread {
            val ticket = dataSource.getTicketById(ticketId)
            mainThreadExecutor.execute {
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
            val executor = DefaultMainThreadExecutor()
            return BookingCompletePresenter(view, dataSource, executor)
        }
    }
}
