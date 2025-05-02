package woowacourse.movie.model

enum class SeatGrade(val price: Int) {
    B(10000),
    S(15000),
    A(12000),
    ;

    companion object {
        fun fromRow(row: Int): SeatGrade {
            return when (row) {
                0, 1 -> B
                2, 3 -> S
                4 -> A
                else -> throw IllegalArgumentException("지원하지 않는 행입니다")
            }
        }
    }
}
