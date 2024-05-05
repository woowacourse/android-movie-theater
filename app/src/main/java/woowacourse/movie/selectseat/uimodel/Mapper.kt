package woowacourse.movie.selectseat.uimodel

import woowacourse.movie.model.Seat
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Tier
import woowacourse.movie.selectseat.parcelable.ParcelablePosition
import woowacourse.movie.selectseat.parcelable.ParcelableSeatUiModel

fun Seats.toSeatUiModelMap() =
    seats.associate {
        Position(it.row, it.col) to SeatUiModel(it.row, it.col, it.rate)
    }

fun Map<Position, SeatUiModel>.toParcelable() =
    map { (position, seatUiModel) ->
        ParcelablePosition(position) to ParcelableSeatUiModel(seatUiModel)
    }.toMap()

fun Map<ParcelablePosition, ParcelableSeatUiModel>.toUiModel() =
    map { (position, seatUiModel) ->
        position.toUiModel() to seatUiModel.toUiModel()
    }.toMap()

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
