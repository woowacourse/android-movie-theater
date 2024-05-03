package woowacourse.movie.selectseat.uimodel

import woowacourse.movie.model.Seat
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Tier

fun Seats.toSeatsUiModel() = seats.map { SeatUiModel(it.row, it.col, it.rate) }

fun List<SeatUiModel>.toSeats() =
    this.map {
        Seat(
            it.rateColor.toRate(),
            it.showPosition[0].uppercaseChar() - 'A' + 1,
            it.showPosition[1].toString().toInt(),
        )
    }

private fun RateColor.toRate() =
    when (this) {
        RateColor.GREEN -> Tier.S
        RateColor.BLUE -> Tier.A
        RateColor.PURPLE -> Tier.B
    }
