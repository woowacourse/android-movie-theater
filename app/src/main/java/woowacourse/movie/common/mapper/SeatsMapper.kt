package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.MovieSeatMapper.toDomain
import woowacourse.movie.common.mapper.MovieSeatMapper.toView
import woowacourse.movie.common.model.SeatsViewData
import woowacourse.movie.domain.seat.Seats

object SeatsMapper : Mapper<Seats, SeatsViewData> {
    override fun Seats.toView(): SeatsViewData {
        return SeatsViewData(
            value.map { it.toView() }
        )
    }

    override fun SeatsViewData.toDomain(): Seats {
        return Seats(
            value.map { it.toDomain() }
        )
    }
}
