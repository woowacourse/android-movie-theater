package woowacourse.movie.ui.complete

import woowacourse.movie.model.movie.UserTicket
import woowacourse.movie.ui.HandleError

interface MovieReservationCompleteContract {
    interface View : HandleError

    interface Presenter {
        fun loadTicket(ticketId: Long)

        fun handleError(throwable: Throwable)
    }
}
