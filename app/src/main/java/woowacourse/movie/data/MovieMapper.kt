package woowacourse.movie.data

import woowacourse.movie.data.entity.ReservationEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun Reservation.toReservationEntity(): ReservationEntity {
    return ReservationEntity(
        cinemaName = cinemaName,
        movieName = title.name,
        synopsis = synopsis.content,
        runningTime = runningTime.time.toString(),
        releaseDate = releaseDate.toFormattedString()
    )
}

fun Reservation.toSeatEntities(reservationId: Long): List<SeatEntity> {
    return seats.map {
        SeatEntity(
            row = it.row - 'A',
            col = it.number,
            grade = it.grade,
            reservationId = reservationId
        )
    }
}


fun Map<ReservationEntity, List<SeatEntity>>.toReservation(): Reservation {
    val reservationEntity = keys.first()
    val seats = values.flatten()
        .map { Seat('A' + it.row, it.col, it.grade, true) }
        .toSet()
    return Reservation(
        id = reservationEntity.id,
        cinemaName = reservationEntity.cinemaName,
        title = Title(reservationEntity.movieName),
        releaseDate = reservationEntity.releaseDate.toMovieDate(),
        runningTime = RunningTime(reservationEntity.runningTime.toInt()),
        synopsis = Synopsis(reservationEntity.synopsis),
        seats = seats
    )
}

fun Map<ReservationEntity, List<SeatEntity>>.toReservations(): List<Reservation> {
    return keys.map { reservation ->
        val seats = get(reservation)
            ?.map { Seat('A' + it.row, it.col, it.grade, true) }
            ?.toSet() ?: emptySet()
        Reservation(
            id = reservation.id,
            cinemaName = reservation.cinemaName,
            title = Title(reservation.movieName),
            releaseDate = reservation.releaseDate.toMovieDate(),
            runningTime = RunningTime(reservation.runningTime.toInt()),
            synopsis = Synopsis(reservation.synopsis),
            seats = seats
        )
    }
}

private fun MovieDate.toFormattedString(): String = date.format(Formatter)

private fun String.toMovieDate(): MovieDate = LocalDate.parse(this, Formatter).let(::MovieDate)