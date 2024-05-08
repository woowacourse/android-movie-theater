package woowacourse.movie.data

data class ReservationHistoryEntity(
    val id: Long,
    val title: String,
    val date: String,
    val time: String,
    val count: Int,
    val seats: String,
    val theaterName: String,
)
