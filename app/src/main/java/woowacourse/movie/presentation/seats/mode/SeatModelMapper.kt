package woowacourse.movie.presentation.seats.mode

import woowacourse.movie.domain.model.seat.Seat

fun Seat.toUiModel(): SeatUiModel =
    SeatUiModel(
        row = this.row,
        col = this.col,
    )

fun SeatUiModel.toDomain(): Seat =
    Seat.from(
        row = this.row,
        col = this.col,
    )
