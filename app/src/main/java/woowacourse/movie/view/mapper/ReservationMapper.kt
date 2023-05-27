package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.Price
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.mapper.MovieMapper.toDomain
import woowacourse.movie.view.mapper.MovieMapper.toView
import woowacourse.movie.view.mapper.ReservationDetailMapper.toDomain
import woowacourse.movie.view.mapper.ReservationDetailMapper.toView
import woowacourse.movie.view.mapper.SeatsMapper.toDomain
import woowacourse.movie.view.mapper.SeatsMapper.toView

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
