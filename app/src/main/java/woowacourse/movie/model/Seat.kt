package woowacourse.movie.model

data class Seat(
    val seatName: String,
    val isSelected: Boolean = false,
) {
    val grade: SeatGrade = SeatGrade.fromRow(seatName.first())

    init {
        require(seatName.isNotBlank()) { ERROR_EMPTY_SEAT }
        require(seatName.first() in SEATS) {
            ERROR_INVALID_SEATS
        }
    }

    companion object {
        private const val ERROR_EMPTY_SEAT = "좌석 이름은 비어 있을 수 없습니다."
        private val SEATS = listOf('A', 'B', 'C', 'D', 'E')
        private const val ERROR_INVALID_SEATS = "지원하지 않는 좌석 행입니다"
    }
}
