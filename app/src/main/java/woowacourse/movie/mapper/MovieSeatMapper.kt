package woowacourse.movie.mapper

import woowacourse.movie.data.SeatViewData
import woowacourse.movie.domain.seat.MovieSeatRow
import woowacourse.movie.domain.seat.Seat
import woowacourse.movie.mapper.MovieColorMapper.matchColor

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
