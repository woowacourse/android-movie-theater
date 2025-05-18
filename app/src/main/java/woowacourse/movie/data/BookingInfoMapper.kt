package woowacourse.movie.data

import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieSeatUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.SeatTypeUiModel

fun BookingInfoUiModel.toEntity(): BookingWithSeatsEntity {
    val bookingEntity =
        BookingInfoEntity(
            movieTitle = movie.title,
            theaterName = theaterName,
            date = "${date.year}.${date.month}.${date.day}",
            time = "${movieTime.hour}:${movieTime.minute}",
            ticketCount = ticketCount,
            totalPrice = totalPrice,
        )

    val seatEntities =
        selectedSeats.map { seat ->
            BookingSeatEntity(
                bookingId = 0,
                row = seat.row,
                column = seat.column,
            )
        }

    return BookingWithSeatsEntity(
        booking = bookingEntity,
        selectedSeats = seatEntities,
    )
}

fun BookingWithSeatsEntity.toUiModel(): BookingInfoUiModel {
    val seats =
        selectedSeats
            .map { seatEntity ->
                MovieSeatUiModel(
                    row = seatEntity.row,
                    column = seatEntity.column,
                    seatType = SeatTypeUiModel.valueOf(SeatTypeUiModel.NONE.name),
                )
            }.toSet()

    val (year, month, day) = booking.date.split(".").map { it.toInt() }
    val (hour, minute) = booking.time.split(":").map { it.toInt() }

    return BookingInfoUiModel(
        movie = MovieUiModel(title = booking.movieTitle),
        theaterName = booking.theaterName,
        date = MovieDateUiModel(year, month, day),
        movieTime = MovieTimeUiModel(hour, minute),
        ticketCount = booking.ticketCount,
        totalPrice = booking.totalPrice,
        selectedSeats = seats,
        isRequiredSeatsSelected = seats.isNotEmpty(),
    )
}
