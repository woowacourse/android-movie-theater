package woowacourse.movie.data

import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieSeatUiModel
import woowacourse.movie.feature.model.MovieTimeUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.SeatTypeUiModel

fun BookingInfoUiModel.toEntity(): BookingHistoryDetails {
    val seatLabels =
        selectedSeats
            .sortedWith(compareBy({ it.row }, { it.column })) // 일관된 순서
            .joinToString(", ") { it.toLabel() }

    return BookingHistoryDetails(
        movieTitle = movie.title,
        theaterName = theaterName,
        date = "${date.year}.${date.month}.${date.day}",
        time = "${movieTime.hour}:${movieTime.minute}",
        ticketCount = ticketCount,
        totalPrice = totalPrice,
        selectedSeats = seatLabels,
    )
}

fun BookingHistoryDetails.toUiModel(): BookingInfoUiModel {
    val seats =
        selectedSeats
            .split(", ")
            .map { label ->
                val row = label[0] - 'A' + 1
                val col = label.substring(1).toInt()
                MovieSeatUiModel(row, col, seatType = SeatTypeUiModel.NONE)
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
