package woowacourse.movie.data.model

data class MovieBookingEntity(
    val title: String,
    val date: String,
    val time: String,
    val personCount: Int,
    val seatNames: String,
    val totalPrice: Int,
    val id: Long = -1L
)