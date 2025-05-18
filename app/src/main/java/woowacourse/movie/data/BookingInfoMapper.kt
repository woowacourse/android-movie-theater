package woowacourse.movie.data

import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieSeatUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.SeatTypeUiModel
import woowacourse.movie.util.SeatLabelFormatter.formatLabel
import woowacourse.movie.util.SeatLabelFormatter.parseLabel

fun BookingInfoUiModel.toEntity(): BookingInfoEntity {
    val seatLabels =
        selectedSeats
            .sortedWith(compareBy({ it.row }, { it.column }))
            .joinToString { seat ->
                formatLabel(seat.row, seat.column)
            }

    return BookingInfoEntity(
        movieTitle = movie.title,
        theaterName = theaterName,
        date = "${date.year}.${date.month}.${date.day}",
        time = "${movieTime.hour}:${movieTime.minute}",
        ticketCount = ticketCount,
        totalPrice = totalPrice,
        selectedSeats = seatLabels,
    )
}

fun BookingInfoEntity.toUiModel(): BookingInfoUiModel {
    val seats =
        selectedSeats
            .split(", ")
            .mapNotNull { label ->
                parseLabel(label)?.let { (row, col) ->
                    MovieSeatUiModel(row, col, seatType = SeatTypeUiModel.NONE)
                }
            }.toSet()

    val (year, month, day) = date.split(".").map { it.toInt() }
    val (hour, minute) = time.split(":").map { it.toInt() }

    return BookingInfoUiModel(
        movie = MovieUiModel(title = movieTitle),
        theaterName = theaterName,
        date = MovieDateUiModel(year, month, day),
        movieTime = MovieTimeUiModel(hour, minute),
        ticketCount = ticketCount,
        totalPrice = totalPrice,
        selectedSeats = seats,
        isRequiredSeatsSelected = seats.isNotEmpty(),
    )
}
