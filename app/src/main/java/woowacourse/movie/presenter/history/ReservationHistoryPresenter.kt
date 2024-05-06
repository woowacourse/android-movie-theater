package woowacourse.movie.presenter.history

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.repository.ReservationTicketRepository

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationTicketRepository: ReservationTicketRepository,
) : ReservationHistoryContract.Presenter {
    override suspend fun loadReservationTickets() {
        withContext(Dispatchers.IO){
            val tickets = reservationTicketRepository.loadReservationTickets()

            withContext(Dispatchers.Main){
                view.showReservationHistory(tickets)
            }
        }
    }

    override fun loadReservationTicket(reservationTicket: ReservationTicket) {
        view.navigateToDetail(reservationTicket)
    }
}
