package woowacourse.movie.ui.booking

import android.os.Handler
import android.os.Looper
import woowacourse.movie.model.db.UserTicketRepository

class MovieBookingHistoryPresenter(
    private val view: MovieBookingHistoryContract.View,
) :
    MovieBookingHistoryContract.Presenter {
    override fun loadBookingHistories() {
        val userTicketRepository = UserTicketRepository.instance()
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val userTickets = userTicketRepository.getUserTickets()
            handler.post {
                view.showBookingHistories(userTickets)
            }
        }.start()
    }
}
