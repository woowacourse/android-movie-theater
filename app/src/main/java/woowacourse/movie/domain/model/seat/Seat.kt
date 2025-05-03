package woowacourse.movie.domain.model.seat

import java.io.Serializable

data class Seat(
    val row: Int,
    val col: Int,
) : Serializable {
    val grade: SeatGrade = SeatGrade.of(row)
}
