package woowacourse.movie.reservation

import woowacourse.movie.ui.model.TicketUiModel

fun interface TicketClickListener {
    fun onTicketClick(ticket: TicketUiModel)
}
