package woowacourse.movie.domain.discountPolicy

import woowacourse.movie.domain.model.Price
import woowacourse.movie.domain.model.ReservationDetail

class Discount(private val discountPolicy: List<DiscountPolicy>) {
    fun calculate(
        reservationDetail: ReservationDetail,
        price: Price
    ): Price {
        return discountPolicy.fold(price) { total, item ->
            item.discount(reservationDetail, total)
        }
    }
}
