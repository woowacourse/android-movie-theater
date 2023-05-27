package woowacourse.movie.domain.discountPolicy

import woowacourse.movie.domain.model.Price
import woowacourse.movie.domain.model.ReservationDetail

interface DiscountPolicy {
    fun discount(reservationDetail: ReservationDetail, price: Price): Price
}
