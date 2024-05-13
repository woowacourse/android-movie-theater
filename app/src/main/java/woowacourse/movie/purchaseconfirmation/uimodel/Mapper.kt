package woowacourse.movie.purchaseconfirmation.uimodel

import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seats

fun Reservation.toPurchaseConfirmationUiModel(): PurchaseConfirmationUiModel {
    return PurchaseConfirmationUiModel(
        screening.movie.title,
        cancelDeadLine,
        screening.localDateTime,
        seats.count,
        seats.toSeatUiModels(),
        totalPrice.price,
        screening.theater.name,
    )
}

fun Seats.toSeatUiModels(): List<SeatUiModel> = seats.map { SeatUiModel(it.row, it.col) }
