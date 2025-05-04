package woowacourse.movie.domain.model

data class ReservationUiModel(
    val title: String,
    val movieDate: String,
    val movieTime: String,
    val ticketCount: Int,
    val theaterName: String,
)
