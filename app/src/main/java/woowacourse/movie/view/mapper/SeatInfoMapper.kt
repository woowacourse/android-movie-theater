package woowacourse.movie.view.mapper

import woowacourse.movie.R
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.domain.theater.SeatInfo
import woowacourse.movie.view.model.SeatInfoUiModel

fun SeatInfo.toUiModel(colorOfGrade: Map<Grade, Int>): SeatInfoUiModel {
    val colorOfRow: MutableMap<Int, Int> = mutableMapOf()
    repeat(size.row) {
        colorOfRow[it] = colorOfGrade[gradeOfRow[it]] ?: R.color.black
    }
    return SeatInfoUiModel(size.row, size.col, colorOfRow.toMap()) // UI그리기를 위함
}
