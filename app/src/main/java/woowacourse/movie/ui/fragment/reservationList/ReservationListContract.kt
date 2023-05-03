package woowacourse.movie.ui.fragment.reservationList

import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.model.TicketsState

interface ReservationListContract {
    interface View

    interface Presenter {
        fun getReservationList(): List<TicketsState> {
            return TicketsRepository.allTickets()
        }
    }
}
