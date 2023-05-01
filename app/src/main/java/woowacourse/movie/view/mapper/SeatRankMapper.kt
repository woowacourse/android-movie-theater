package woowacourse.movie.view.mapper

import domain.seatPolicy.SeatRank
import woowacourse.movie.view.model.SeatRankUiModel

object SeatRankMapper : DomainViewMapper<SeatRank, SeatRankUiModel> {
    override fun SeatRankUiModel.toDomain(): SeatRank {
        return SeatRank.valueOf(name)
    }

    override fun SeatRank.toUi(): SeatRankUiModel {
        return SeatRankUiModel.valueOf(name)
    }
}
