package woowacourse.movie.domain.model.seat

enum class SeatGrade(
    val price: Int,
) {
    S(15_000),
    A(12_000),
    B(10_000),
    ;

    companion object {
        fun of(row: Int): SeatGrade =
            when (row) {
                2, 3 -> S
                4 -> A
                0, 1 -> B
                else -> throw IllegalArgumentException(GRADE_ERROR)
            }

        private const val GRADE_ERROR = "[ERROR] 해당하는 좌석 등급이 존재하지 않습니다."
    }
}
