package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.MovieColorMapper.matchColor
import woowacourse.movie.common.model.SeatViewData
import woowacourse.movie.domain.seat.MovieSeatRow
import woowacourse.movie.domain.seat.Seat

object MovieSeatMapper : Mapper<Seat, SeatViewData> {
    override fun Seat.toView(): SeatViewData {
        return SeatViewData(
            row.row, column, row.seatRankByRow().matchColor()
        )
    }

    override fun SeatViewData.toDomain(): Seat {
        return Seat(
            MovieSeatRow(row), column
        )
    }
}
