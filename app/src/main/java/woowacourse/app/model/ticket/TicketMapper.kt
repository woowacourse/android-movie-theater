package woowacourse.app.model.ticket

import woowacourse.app.model.movie.MovieMapper.toUiModel
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.Ticket

object TicketMapper {
    fun Ticket.toUiModel(): TicketUiModel {
        return TicketUiModel(
            movie = this.movie.toUiModel(),
            bookedDateTime = this.bookedDateTime,
            seat = this.seat.toUiModel(),
        )
    }

    fun Seat.toUiModel(): SeatUiModel {
        return SeatUiModel(
            rank = this.rank,
            position = this.position.toUiModel(),
        )
    }

    fun SeatUiModel.toDomainModel(): Seat {
        return Seat(
            rank = this.rank,
            position = this.position.toDomainModel(),
        )
    }

    fun Position.toUiModel(): PositionUiModel {
        return PositionUiModel(
            row = this.row,
            column = this.column,
        )
    }

    fun PositionUiModel.toDomainModel(): Position {
        return Position(
            row = this.row,
            column = this.column,
        )
    }
}
