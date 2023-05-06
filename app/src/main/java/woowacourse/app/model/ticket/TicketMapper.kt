package woowacourse.app.model.ticket

import woowacourse.app.model.movie.MovieMapper.toUiModel
import woowacourse.app.model.seat.SeatMapper.toUiModel
import woowacourse.domain.ticket.Ticket

object TicketMapper {
    fun Ticket.toUiModel(): TicketUiModel {
        return TicketUiModel(
            movie = this.movie.toUiModel(),
            bookedDateTime = this.bookedDateTime,
            seat = this.seat.toUiModel(),
        )
    }
}
