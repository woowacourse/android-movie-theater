package woowacourse.movie.data.model.mapper

import domain.Seat
import domain.seatPolicy.SeatPolicies
import woowacourse.movie.data.model.uimodel.SeatUIModel

object SeatMapper : DomainViewMapper<Seat, SeatUIModel> {
    override fun toDomain(seatUIModel: SeatUIModel): Seat {
        return Seat(
            SeatUIModel.toNumber(seatUIModel.row),
            seatUIModel.col,
            SeatPolicies()
        )
    }

    override fun toUI(domainModel: Seat): SeatUIModel {
        return SeatUIModel(
            SeatUIModel.toChar(domainModel.row),
            domainModel.col
        )
    }
}
