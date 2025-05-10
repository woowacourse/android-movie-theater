package woowacourse.movie.presentation.bookinghistory

import androidx.fragment.app.Fragment
import woowacourse.movie.data.bookinghistory.BookingHistoryDatabase
import woowacourse.movie.data.bookinghistory.BookingHistoryMapper
import woowacourse.movie.domain.model.movie.MovieTicket
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
    private val database: BookingHistoryDatabase,
) : BookingHistoryContract.Presenter {
    override fun loadBookingHistory() {
        thread {
            val dao = database.bookingHistoryDao()
            val tickets = dao.getAll().map { BookingHistoryMapper.mapFromBookingHistory(it) }
            (view as? Fragment)?.requireActivity()?.runOnUiThread {
                view.showBookingHistory(tickets)
            }
        }
    }

    override fun selectBookingHistory(ticket: MovieTicket) {
        view.navigateToBookingSummary(ticket)
    }
}
