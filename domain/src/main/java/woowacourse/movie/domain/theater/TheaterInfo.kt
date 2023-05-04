package woowacourse.movie.domain.theater

import woowacourse.movie.domain.price.Price

class TheaterInfo(
    private val rowGrade: Map<Int, Grade>,
    private val seatRow: Int,
    private val seatCol: Int
) {
    fun getGradePrice(row: Int): Price? = rowGrade[row]?.price
    fun getRowGrade(row: Int): Grade? = rowGrade[row]
    fun isValidSeat(selectRow: Int, selectCol: Int): Boolean = selectRow < seatRow && selectCol < seatCol
}
