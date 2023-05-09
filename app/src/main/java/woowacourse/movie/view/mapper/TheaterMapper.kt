package woowacourse.movie.view.mapper

import woowacourse.movie.R
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.view.model.TheaterUiModel

fun Theater.toUiModel(colorOfGrade: Map<Grade, Int>): TheaterUiModel {
    val colorOfRow: MutableMap<Int, Int> = mutableMapOf()
    repeat(size.row) {
        colorOfRow[it] = colorOfGrade[gradeOfRow[it]] ?: R.color.black
    }
    return TheaterUiModel(name, size.row, size.col, colorOfRow.toMap())
}
