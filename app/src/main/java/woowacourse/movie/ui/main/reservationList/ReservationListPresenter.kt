package woowacourse.movie.ui.main.reservationList

import com.example.domain.repository.TicketsRepository
import woowacourse.movie.model.mapper.asPresentation

class ReservationListPresenter(
    private val view: ReservationListContract.View,
    private val ticketsRepository: TicketsRepository = TicketsRepository
) : ReservationListContract.Presenter {
    override fun getReservationList() {
        view.setAdapter(ticketsRepository.allTickets().map { it.asPresentation() })
    }
}
