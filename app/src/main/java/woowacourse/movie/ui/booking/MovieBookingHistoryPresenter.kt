package woowacourse.movie.ui.booking

import android.os.Handler
import android.os.Looper
import woowacourse.movie.model.db.UserTicketDatabase

class MovieBookingHistoryPresenter(
    private val view: MovieBookingHistoryContract.View,
) :
    MovieBookingHistoryContract.Presenter {
    override fun loadBookingHistories() {
        val db = UserTicketDatabase.database()
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val userTickets = db.userTicketDao().findAll()
            handler.post {
                view.showBookingHistories(userTickets)
            }
        }.start()
    }
}
