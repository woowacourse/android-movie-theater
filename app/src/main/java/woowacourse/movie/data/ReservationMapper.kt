package woowacourse.movie.data

import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ticket.MovieTicket

fun MovieTicket.toEntity(): MovieTicketEntity =
    MovieTicketEntity(
        reservationInfoEntity =
            ReservationInfoEntity(
                movieId = movie.id,
                theaterId = theater.id,
                movieDateYear = movieDate.year,
                movieDateMonth = movieDate.month.value,
                movieDateDay = movieDate.dayOfMonth,
                movieTimeHour = movieTime.value.hour,
                movieTimeMinute = movieTime.value.minute,
                ticketCount = seats.size,
                price = price,
            ),
        seats = seats.map { it.toEntity(0L) },
        movie = movie.toEntity(),
        theater = theater.toEntity(),
    )

fun Seat.toEntity(reservationId: Long): SeatEntity =
    SeatEntity(
        reservationId = reservationId,
        row = row.value,
        column = column.value,
    )

fun Movie.toEntity(): MovieEntity =
    MovieEntity(
        id = id,
        title = title,
    )

fun Theater.toEntity(): TheaterEntity =
    TheaterEntity(
        id = id,
        name = name,
    )
