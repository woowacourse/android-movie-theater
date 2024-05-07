package woowacourse.movie.feature.result

import android.os.Handler
import android.os.Looper
import woowacourse.movie.data.TicketRepository

class MovieResultPresenter(private val view: MovieResultContract.View) :
    MovieResultContract.Presenter {
    override fun loadTicket(
        ticketRepository: TicketRepository,
        ticketId: Long,
    ) {
//        val ticket =
//            runCatching {
//                ticketRepository.find(ticketId)
//            }.getOrElse {
//                view.showToastInvalidMovieIdError(it)
//                return
//            }
//        view.displayTicket(ticket)

        val handler = Handler(Looper.getMainLooper())
        Thread {
            val ticket = ticketRepository.find(ticketId)
            handler.post {
                view.displayTicket(ticket)
            }
        }.start()
    }
}
