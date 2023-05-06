package woowacourse.app.presentation.model.seat

import woowacourse.domain.SelectedSeat
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat

object SeatMapper {

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

    fun SelectedSeat.toUiModel(): SelectedSeatUiModel {
        return SelectedSeatUiModel(this.seats.map { it.toUiModel() }.toSet())
    }
}
