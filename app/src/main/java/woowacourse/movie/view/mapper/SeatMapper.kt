package woowacourse.movie.view.mapper

import androidx.annotation.ColorInt
import woowacourse.movie.domain.system.Seat
import woowacourse.movie.view.model.SeatUiModel

fun Seat.toUiModel(@ColorInt color: Int = 0): SeatUiModel = SeatUiModel(row, col, color)
fun SeatUiModel.toDomain(): Seat = Seat(row, col)
