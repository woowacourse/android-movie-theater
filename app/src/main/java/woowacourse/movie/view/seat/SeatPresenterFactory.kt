package woowacourse.movie.view.seat

import android.content.Context
import woowacourse.movie.data.datasource.TicketDataSourceImpl
import woowacourse.movie.data.db.UserDatabase
import woowacourse.movie.domain.model.Booking
import woowacourse.movie.domain.model.seat.Seats

class SeatPresenterFactory {
    fun initialize(
        view: SeatContract.View,
        booking: Booking,
        context: Context,
    ): SeatContract.Presenter {
        val seat = Seats()
        val db = UserDatabase.getDatabase(context)
        val dao = db.ticketDao()
        val dataSource = TicketDataSourceImpl(dao)
        return SeatPresenter(view, seat, booking, dataSource)
    }
}
