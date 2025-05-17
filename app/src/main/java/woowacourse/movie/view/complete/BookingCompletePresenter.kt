package woowacourse.movie.view.complete

import android.content.Context
import woowacourse.movie.data.datasource.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.Callback
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.model.Ticket

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val dataSource: TicketDataSource,
) : BookingCompleteContract.Presenter {
    override fun loadTicket(
        ticketId: Long,
        requestAlarm: Boolean,
    ) {
        dataSource.getTicketById(
            ticketId,
            object : Callback<Ticket> {
                override fun onSuccess(data: Ticket) {
                    view.showTicket(data)
                    if (requestAlarm) view.generateAlarm(data)
                }

                override fun onError(e: Throwable) {
                    view.showMessage()
                }
            },
        )
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
