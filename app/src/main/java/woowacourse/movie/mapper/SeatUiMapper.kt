package woowacourse.movie.mapper

import woowacourse.movie.model.Seat
import woowacourse.movie.ui.model.SeatUiModel

fun Seat.toUiModel(): SeatUiModel {
    return SeatUiModel(
        row = row,
        col = col,
    )
}

fun SeatUiModel.toDomain(): Seat {
    return Seat(
        row = row,
        col = col,
    )
}
