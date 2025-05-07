package woowacourse.movie.model

data class Seat(
    val row: Int,
    val col: Int,
) {
    val grade: SeatGrade = SeatGrade.fromRow(row)

    init {
        require(row <= MAXIMUM_ROW && col <= MAXIMUM_COL) {
            ERROR_INVALID_SEATS
        }
    }

    companion object {
        private const val MAXIMUM_ROW = 4
        private const val MAXIMUM_COL = 3
        private const val ERROR_INVALID_SEATS = "지원하지 않는 좌석입니다"
    }
}
