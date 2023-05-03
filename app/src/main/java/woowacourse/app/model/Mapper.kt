package woowacourse.app.model

import woowacourse.app.model.main.MovieMapper.toUiModel
import woowacourse.domain.SelectedSeat
import woowacourse.domain.reservation.Reservation
import woowacourse.domain.ticket.Position
import woowacourse.domain.ticket.Seat
import woowacourse.domain.ticket.Ticket

object Mapper {
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

    fun Reservation.toUiModel(): ReservationUiModel {
        return ReservationUiModel(
            id = this.id,
            tickets = this.tickets.map { it.toUiModel() }.toSet(),
            paymentType = this.paymentType,
            payment = this.payment,
            movie = this.movie.toUiModel(),
            movieTitle = this.tickets.first().movie.title,
            bookedDateTime = this.bookedDateTime,
            count = this.count,
            alarmCycle = this.alarmCycle,
        )
    }

    fun SelectedSeat.toUiModel(): SelectedSeatUiModel {
        return SelectedSeatUiModel(this.seats.map { it.toUiModel() }.toSet())
    }
}
