package woowacourse.movie.model.mapper

import domain.seatPolicy.SeatRank
import woowacourse.movie.model.SeatRankUiModel

object SeatRankMapper : DomainViewMapper<SeatRank, SeatRankUiModel> {
    override fun SeatRankUiModel.toDomain(): SeatRank {
        return SeatRank.valueOf(name)
    }

    override fun SeatRank.toUi(): SeatRankUiModel {
        return SeatRankUiModel.valueOf(name)
    }
}
