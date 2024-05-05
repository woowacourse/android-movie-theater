package woowacourse.movie.selectseat

import woowacourse.movie.model.Seat
import woowacourse.movie.selectseat.uimodel.SeatUiModel

fun List<Seat>.toSeatsUiModel() = this.map { SeatUiModel(it.row, it.col, it.rate) }
