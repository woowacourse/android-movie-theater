package woowacourse.movie.reservationresult

import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.Seat
import woowacourse.movie.reservationresult.uimodel.ReservationResultUiModel
import woowacourse.movie.reservationresult.uimodel.SeatUiModel
import java.time.format.DateTimeFormatter

fun MovieReservation.toReservationResultUiModel(theaterName: String): ReservationResultUiModel {
    return ReservationResultUiModel(
        movie.title,
        cancelDeadLine.inWholeMinutes.toInt(),
        screenDateTime.format(formatter),
        headCount.count,
        selectedSeats.selectedSeats.toSeatUiModel(),
        theaterName,
        totalPrice.price,
    )
}

fun List<Seat>.toSeatUiModel(): List<SeatUiModel> = this.map { SeatUiModel(it.row, it.col) }

private val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
