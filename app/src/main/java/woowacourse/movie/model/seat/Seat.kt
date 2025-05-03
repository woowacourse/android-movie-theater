package woowacourse.movie.model.seat

data class Seat(
    val row: Row,
    val col: Col,
    val isSelected: Boolean = false,
) {
    val grade: SeatGrade = SeatGrade.fromRow(row.value)
}
