package woowacourse.app.model

import woowacourse.app.model.movie.MovieMapper.toDomainModel
import woowacourse.app.model.movie.MovieMapper.toUiModel
import woowacourse.app.model.ticket.TicketMapper.toUiModel
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

    fun BookedMovieUiModel.toBookedMovie(): BookedMovie {
        return BookedMovie(
            movie = this.movie.toDomainModel(),
            theaterId = this.theaterId,
            ticketCount = this.ticketCount,
            bookedDateTime = this.bookedDateTime,
        )
    }
}
