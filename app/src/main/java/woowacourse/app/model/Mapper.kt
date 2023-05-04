package woowacourse.app.model

import woowacourse.app.model.movie.MovieMapper.toUiModel
import woowacourse.app.model.ticket.TicketMapper.toUiModel
import woowacourse.domain.SelectedSeat
import woowacourse.domain.reservation.Reservation

object Mapper {

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
            alarmCycle = Reservation.alarmCycle,
        )
    }

    fun List<Reservation>.toUiReservations(): List<ReservationUiModel> {
        return this.map { it.toUiModel() }
    }

    fun SelectedSeat.toUiModel(): SelectedSeatUiModel {
        return SelectedSeatUiModel(this.seats.map { it.toUiModel() }.toSet())
    }
}
