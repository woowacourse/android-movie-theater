package woowacourse.movie.data.model.mapper

import domain.seatPolicy.SeatRank
import woowacourse.movie.data.model.uimodel.SeatRankUiModel

object SeatRankMapper : DomainViewMapper<SeatRank, SeatRankUiModel> {
    override fun toDomain(seatRankUiModel: SeatRankUiModel): SeatRank {
        return SeatRank.valueOf(seatRankUiModel.name)
    }

    override fun toUi(domainModel: SeatRank): SeatRankUiModel {
        return SeatRankUiModel.valueOf(domainModel.name)
    }
}
