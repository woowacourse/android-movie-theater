package woowacourse.movie.data.model

data class MovieBookingEntity(
    val id: Long,
    val title: String,
    val date: String,
    val time: String,
    val personCount: String,
    val seatNames: String,
    val totalPrice: Int
)