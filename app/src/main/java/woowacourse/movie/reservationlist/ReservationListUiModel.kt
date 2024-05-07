package woowacourse.movie.reservationlist

data class ReservationListUiModel(
    val id: Long,
    val date: String,
    val time: String,
    val theaterName: String,
    val title: String,
)
