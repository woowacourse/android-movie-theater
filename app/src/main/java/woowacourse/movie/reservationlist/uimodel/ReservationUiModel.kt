package woowacourse.movie.reservationlist.uimodel

data class ReservationUiModel(
    val id: Long,
    val date: String,
    val time: String,
    val theater: String,
    val movieTitle: String,
)
