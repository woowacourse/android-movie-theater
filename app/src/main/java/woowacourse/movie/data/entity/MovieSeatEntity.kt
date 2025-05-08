package woowacourse.movie.data.entity

data class MovieSeatEntity(
    val row: Int,
    val column: Int,
    val seatType: String,
    val isSelected: Boolean,
)
