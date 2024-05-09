package woowacourse.movie.presentation.homefragments.reservationList.uiModel

data class ReservationItemUiModel(
    val id: Long,
    val movieTitle: String,
    val screenDate: String,
    val screenTime: String,
    val theaterName: String?,
)
