package woowacourse.movie.model

enum class SeatGrade(
    val price: Int,
) {
    B(10_000),
    S(15_000),
    A(12_000),
    ;

    companion object {
        fun getSeatGrade(seat: Seat): SeatGrade {
            val grade = seat.rowLabel.uppercase()
            return when (grade) {
                "A", "B" -> B
                "C", "D" -> S
                "E" -> A
                else -> throw IllegalArgumentException("유효하지 않은 좌석 등급입니다.")
            }
        }
    }
}
