package woowacourse.movie.view.complete

import android.content.Context
import woowacourse.movie.data.datasource.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase

class BookingCompletePresenterFactory(
    private val view: BookingCompleteContract.View,
    private val context: Context,
) {
    fun initialize(

    ): BookingCompleteContract.Presenter {
        val dao = UserDatabase.getDatabase(context).ticketDao()
        val dataSource = TicketDataSourceImpl(dao)
        return BookingCompletePresenter(view, dataSource)
    }
}
