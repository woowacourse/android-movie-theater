package woowacourse.movie.ui.booking

import android.os.Handler
import android.os.Looper
import woowacourse.movie.model.db.UserTicketRepository

class MovieBookingHistoryPresenter(
    private val view: MovieBookingHistoryContract.View,
    private val userTicketRepository: UserTicketRepository,
) :
    MovieBookingHistoryContract.Presenter {
    override fun loadBookingHistories() {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val userTickets = userTicketRepository.findAll()
            handler.post {
                view.showBookingHistories(userTickets)
            }
        }.start()
    }
}
