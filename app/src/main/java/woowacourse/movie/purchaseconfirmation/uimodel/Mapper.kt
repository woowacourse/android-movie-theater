package woowacourse.movie.purchaseconfirmation.uimodel

import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seat

fun Reservation.toReservationResultUiModel(theaterName: String): PurchaseConfirmationUiModel {
    return PurchaseConfirmationUiModel(
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
