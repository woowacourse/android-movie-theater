package woowacourse.movie.policy

import java.time.LocalDateTime

object RunningDiscountPolicy : TicketDiscountPolicy {

    private val discountPolices = listOf(
        MovieDayDiscountPolicy(),
        EarlyNightDiscountPolicy(),
    )

    override fun discount(price: Int, bookedDateTime: LocalDateTime): Int {
        var discountedPrice = price

        discountPolices.forEach { policy ->
            discountedPrice = policy.discount(discountedPrice, bookedDateTime)
        }

        return discountedPrice
    }
}
