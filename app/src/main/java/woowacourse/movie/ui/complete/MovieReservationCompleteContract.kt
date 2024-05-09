package woowacourse.movie.ui.complete

import woowacourse.movie.model.movie.TicketEntity
import woowacourse.movie.ui.HandleError

interface MovieReservationCompleteContract {
    interface View : HandleError {
        fun showReservationResult(userTicket: TicketEntity)
    }

    interface Presenter {
        fun loadTicket(ticketId: Long)

        fun handleError(throwable: Throwable)
    }
}
