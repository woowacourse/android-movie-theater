package woowacourse.movie.ui.history

import woowacourse.movie.domain.UserTicket
import woowacourse.movie.ui.HandleError

interface BookingHistoryContract {
    interface View : HandleError {
        fun showHistoryItems(items: List<UserTicket>)
    }

    interface Presenter {
        fun loadHistoryItems()
    }
}
