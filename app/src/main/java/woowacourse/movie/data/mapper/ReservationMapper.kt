package woowacourse.movie.data.mapper

import woowacourse.movie.data.entity.MovieEntity
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TheaterEntity
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.SeatGridElement
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ticket.MovieTicket
import java.time.LocalDate
import java.time.LocalTime

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
        poster = poster,
        movieStartDateYear = startDate.year,
        movieStartDateMonth = startDate.monthValue,
        movieStartDateDay = startDate.dayOfMonth,
        movieEndDateYear = endDate.year,
        movieEndDateMonth = endDate.monthValue,
        movieEndDateDay = endDate.dayOfMonth,
        runningTime = runningTime,
    )

fun Theater.toEntity(): TheaterEntity =
    TheaterEntity(
        id = id,
        name = name,
    )

fun MovieTicketEntity.toDomain(): MovieTicket =
    MovieTicket(
        movie = movie.toDomain(),
        theater = theater.toDomain(),
        movieDate =
            LocalDate.of(
                reservationInfoEntity.movieDateYear,
                reservationInfoEntity.movieDateMonth,
                reservationInfoEntity.movieDateDay,
            ),
        movieTime =
            MovieTime(
                LocalTime.of(
                    reservationInfoEntity.movieTimeHour,
                    reservationInfoEntity.movieTimeMinute,
                ),
            ),
        seats = seats.map { it.toDomain() },
    )

fun SeatEntity.toDomain(): Seat =
    Seat(
        row = SeatGridElement(row),
        column = SeatGridElement(column),
    )

fun MovieEntity.toDomain(): Movie =
    Movie(
        id = id,
        title = title,
        poster = poster,
        startDate = LocalDate.of(movieStartDateYear, movieStartDateMonth, movieStartDateDay),
        endDate = LocalDate.of(movieEndDateYear, movieEndDateMonth, movieEndDateDay),
        runningTime = runningTime,
    )

fun TheaterEntity.toDomain(): Theater =
    Theater(
        id = id,
        name = name,
    )
