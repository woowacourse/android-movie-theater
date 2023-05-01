package woowacourse.movie.policy

import java.time.LocalDateTime

class EarlyNightDiscountPolicy : TicketDiscountPolicy {

    override fun discount(price: Int, bookedDateTime: LocalDateTime): Int {
        if (isDiscountable(bookedDateTime)) {
            return price - DISCOUNT_PRICE
        }
        return price
    }

    private fun isDiscountable(bookedDateTime: LocalDateTime): Boolean =
        bookedDateTime.hour in EARLY_TIMES || bookedDateTime.hour in LATE_TIMES

    companion object {
        private const val DISCOUNT_PRICE = 2000
        private val EARLY_TIMES = listOf(9, 10, 11)
        private val LATE_TIMES = listOf(20, 21, 22, 23)
    }
}
