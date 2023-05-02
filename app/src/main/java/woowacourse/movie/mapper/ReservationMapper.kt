package woowacourse.movie.mapper

import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.domain.Price
import woowacourse.movie.domain.Reservation
import woowacourse.movie.mapper.MovieMapper.toDomain
import woowacourse.movie.mapper.MovieMapper.toView
import woowacourse.movie.mapper.ReservationDetailMapper.toDomain
import woowacourse.movie.mapper.ReservationDetailMapper.toView
import woowacourse.movie.mapper.SeatsMapper.toDomain
import woowacourse.movie.mapper.SeatsMapper.toView

object ReservationMapper : Mapper<Reservation, ReservationViewData> {
    override fun Reservation.toView(): ReservationViewData {
        return ReservationViewData(
            movie.toView(),
            reservationDetail.toView(),
            seats.toView(),
            PriceViewData(price.value)
        )
    }

    override fun ReservationViewData.toDomain(): Reservation {
        return Reservation(
            movie.toDomain(),
            reservationDetail.toDomain(),
            seats.toDomain(),
            Price(price.value)
        )
    }
}
