package woowacourse.movie.domain.theater

import woowacourse.movie.domain.price.Price
import java.time.LocalTime

class Theater(
    private val name: String,
    private val rowGrade: Map<Int, Grade>,
    private val seatRow: Int,
    private val seatCol: Int,
    private val screeningMovies: Map<String, List<LocalTime>>,
) {
    fun getGradePrice(row: Int): Price? = rowGrade[row]?.price
    fun getRowGrade(row: Int): Grade? = rowGrade[row]
    fun isValidSeat(selectRow: Int, selectCol: Int): Boolean =
        selectRow < seatRow && selectCol < seatCol

    fun getMovieTimes(title: String): List<LocalTime>? =
        screeningMovies[title] // if null, not screening
}
