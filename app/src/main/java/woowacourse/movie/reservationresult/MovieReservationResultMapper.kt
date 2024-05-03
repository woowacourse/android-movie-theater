package woowacourse.movie.reservationresult

import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seat
import woowacourse.movie.reservationresult.uimodel.ReservationResultUiModel
import woowacourse.movie.reservationresult.uimodel.SeatUiModel

fun Reservation.toReservationResultUiModel(theaterName: String): ReservationResultUiModel {
    return ReservationResultUiModel(
        movie.title,
        cancelDeadLine,
        screenDateTime,
        headCount.count,
        reserveSeats.seats.toSeatUiModel(),
        totalPrice.price,
        theaterName,
    )
}

fun List<Seat>.toSeatUiModel(): List<SeatUiModel> = this.map { SeatUiModel(it.row, it.col) }
