package woowacourse.movie.view.mapper

import woowacourse.movie.domain.system.Seat
import woowacourse.movie.view.model.SeatUiModel

fun Seat.toUiModel(): SeatUiModel = SeatUiModel(row, col)
