package woowacourse.movie.presentation.seat.model

import androidx.annotation.ColorRes
import woowacourse.movie.R
import woowacourse.movie.domain.model.seat.SeatGrade

data class SeatUiModel(
    val row: Int,
    val col: Int,
) {
    private val grade: SeatGrade = SeatGrade.of(row)

    override fun toString(): String = "${'A' + row}${col + 1}"

    @ColorRes
    val colorResId: Int =
        when (grade) {
            SeatGrade.S -> R.color.green
            SeatGrade.A -> R.color.blue
            SeatGrade.B -> R.color.purple
        }
}
