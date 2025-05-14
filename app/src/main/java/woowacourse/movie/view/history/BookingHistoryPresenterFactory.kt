package woowacourse.movie.view.history

import android.content.Context
import woowacourse.movie.data.datasource.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.view.core.util.DefaultMainThreadExecutor

class BookingHistoryPresenterFactory() {
    fun initialize(
        context: Context,
        view: BookingHistoryContract.View,
    ): BookingHistoryContract.Presenter {
        val dao = UserDatabase.getDatabase(context).ticketDao()
        val dataSource = TicketDataSourceImpl(dao)
        val executor = DefaultMainThreadExecutor()
        return BookingHistoryPresenter(view, dataSource, executor)
    }
}
