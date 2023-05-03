package woowacourse.movie.ui.main.reservationList

import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState

class ReservationListPresenter(
    private val ticketsRepository: TicketsRepository = TicketsRepository
) : ReservationListContract.Presenter {
    override fun getReservationList(): List<TicketsState> {
        return ticketsRepository.allTickets()
    }
}
