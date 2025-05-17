package woowacourse.movie.view.history

import android.content.Context
import woowacourse.movie.data.datasource.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase

class BookingHistoryPresenterFactory() {
    fun initialize(
        context: Context,
        view: BookingHistoryContract.View,
    ): BookingHistoryContract.Presenter {
        val dao = UserDatabase.getDatabase(context).ticketDao()
        val dataSource = TicketDataSourceImpl(dao)
        return BookingHistoryPresenter(view, dataSource)
    }
}
