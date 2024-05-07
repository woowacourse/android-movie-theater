package woowacourse.movie.feature.history

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.TicketRepository

class ReservationHistoryPresenter(private val view: ReservationHistoryContract.View) :
    ReservationHistoryContract.Presenter {
    override fun loadTickets(ticketRepository: TicketRepository) {
        val handler = Handler(Looper.getMainLooper())
        Thread {
            val tickets = ticketRepository.findAll()
            handler.post {
                view.displayTickets(tickets)
            }
        }.start()
    }
}
