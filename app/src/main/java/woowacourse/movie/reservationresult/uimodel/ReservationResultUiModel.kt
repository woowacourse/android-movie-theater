package woowacourse.movie.reservationresult.uimodel

data class ReservationResultUiModel(
    val title: String,
    val cancelDeadLine: Int,
    val dateTime: String,
    val headCount: Int,
    val seats: List<SeatUiModel>,
    val theaterName: String,
    val totalPrice: Int,
)
