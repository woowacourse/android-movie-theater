package woowacourse.movie.view.mapper

import domain.Seat
import domain.seatPolicy.SeatPolicies
import woowacourse.movie.view.model.SeatUiModel

object SeatMapper : DomainViewMapper<Seat, SeatUiModel> {
    override fun SeatUiModel.toDomain(): Seat {
        return Seat(
            SeatUiModel.toNumber(row),
            col,
            SeatPolicies()
        )
    }

    override fun Seat.toUi(): SeatUiModel {
        return SeatUiModel(
            SeatUiModel.toChar(row),
            col
        )
    }

}
