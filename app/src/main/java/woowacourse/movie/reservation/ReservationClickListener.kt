package woowacourse.movie.reservation

import woowacourse.movie.ui.model.TicketUiModel

fun interface ReservationClickListener {
    fun onReservationClick(reservation: TicketUiModel)
}
