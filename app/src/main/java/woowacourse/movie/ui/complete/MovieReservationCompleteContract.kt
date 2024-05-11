package woowacourse.movie.ui.complete

import woowacourse.movie.domain.UserTicket
import woowacourse.movie.ui.HandleError

interface MovieReservationCompleteContract {
    interface View : HandleError {
        fun showReservationResult(userTicket: UserTicket)
    }

    interface Presenter {
        fun loadTicket(ticketId: Long)

        fun handleError(throwable: Throwable)
    }
}
