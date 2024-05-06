package woowacourse.movie.presentation.ui.detail

import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.Ticket

interface ScreenDetailContract {
    interface View : BaseView, ScreenDetailActionHandler {
        fun showScreenDetail(screenDetail: ScreenDetailUiModel)

        fun updateTicketCount(ticket: Ticket)

        fun back()
    }

    interface Presenter {
        fun loadScreenDetail(
            movieId: Int,
            theaterId: Int,
        )

        fun plusTicket(ticket: Ticket)

        fun minusTicket(ticket: Ticket)
    }
}
