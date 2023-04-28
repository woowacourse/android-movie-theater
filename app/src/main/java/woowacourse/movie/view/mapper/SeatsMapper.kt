package woowacourse.movie.view.mapper

import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.view.data.SeatsViewData
import woowacourse.movie.view.mapper.MovieSeatMapper.toDomain
import woowacourse.movie.view.mapper.MovieSeatMapper.toView

object SeatsMapper : Mapper<Seats, SeatsViewData> {
    override fun Seats.toView(): SeatsViewData {
        return SeatsViewData(
            value.map { it.toView() }
        )
    }

    override fun SeatsViewData.toDomain(): Seats {
        return Seats(
            seats.map { it.toDomain() }
        )
    }
}
