package woowacourse.movie.domain.model

data class Seat(
    val position: Position,
    private val grade: Grade,
    val selected: Boolean = false,
) {
    fun price() = grade.price
}
