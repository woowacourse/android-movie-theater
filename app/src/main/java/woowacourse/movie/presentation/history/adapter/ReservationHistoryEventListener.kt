package woowacourse.movie.presentation.history.adapter

import woowacourse.movie.presentation.common.model.TicketUiModel

interface ReservationHistoryEventListener {
    fun onHistoryClick(ticket: TicketUiModel)
}
