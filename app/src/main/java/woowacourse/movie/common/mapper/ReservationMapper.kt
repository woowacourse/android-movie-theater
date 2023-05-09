package woowacourse.movie.common.mapper

import woowacourse.movie.common.mapper.MovieMapper.toDomain
import woowacourse.movie.common.mapper.MovieMapper.toView
import woowacourse.movie.common.mapper.ReservationDetailMapper.toDomain
import woowacourse.movie.common.mapper.ReservationDetailMapper.toView
import woowacourse.movie.common.mapper.SeatsMapper.toDomain
import woowacourse.movie.common.mapper.SeatsMapper.toView
import woowacourse.movie.common.model.PriceViewData
import woowacourse.movie.common.model.ReservationViewData
import woowacourse.movie.domain.Price
import woowacourse.movie.domain.Reservation

object ReservationMapper : Mapper<Reservation, ReservationViewData> {
    override fun Reservation.toView(): ReservationViewData {
        return ReservationViewData(
            movie.toView(),
            reservationDetail.toView(),
            seats.toView(),
            PriceViewData(price.value),
            theaterName
        )
    }

    override fun ReservationViewData.toDomain(): Reservation {
        return Reservation(
            movie.toDomain(),
            reservationDetail.toDomain(),
            seats.toDomain(),
            Price(price.value),
            theaterName
        )
    }
}
