package woowacourse.movie.booking.complete

import woowacourse.movie.data.MovieTicketDao
import woowacourse.movie.mapper.toEntity
import woowacourse.movie.ui.model.TicketUiModel
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val ticketDao: MovieTicketDao,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: TicketUiModel

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket
        view.showBookingCompleteResult(ticket)
    }

    override fun saveTicket(ticket: TicketUiModel) {
        thread {
            ticketDao.insertTicket(ticket.toEntity())
        }
    }
}
