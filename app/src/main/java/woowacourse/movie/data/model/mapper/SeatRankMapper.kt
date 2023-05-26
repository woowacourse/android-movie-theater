package woowacourse.movie.data.model.mapper

import domain.seatPolicy.SeatRank
import woowacourse.movie.data.model.uimodel.SeatRankUIModel

object SeatRankMapper : DomainViewMapper<SeatRank, SeatRankUIModel> {
    override fun toDomain(seatRankUIModel: SeatRankUIModel): SeatRank {
        return SeatRank.valueOf(seatRankUIModel.name)
    }

    override fun toUI(domainModel: SeatRank): SeatRankUIModel {
        return SeatRankUIModel.valueOf(domainModel.name)
    }
}
