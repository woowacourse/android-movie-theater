package woowacourse.movie.feature.history

import woowacourse.movie.data.TicketRepository

class ReservationHistoryPresenter(private val view: ReservationHistoryContract.View) :
    ReservationHistoryContract.Presenter {
    override fun loadTickets(ticketRepository: TicketRepository) {
        Thread {
            val tickets = ticketRepository.findAll()
            view.displayTickets(tickets)
        }.start()
    }
}
