package woowacourse.movie.mapper

import woowacourse.movie.data.SeatsViewData
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.mapper.MovieSeatMapper.toDomain
import woowacourse.movie.mapper.MovieSeatMapper.toView

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
