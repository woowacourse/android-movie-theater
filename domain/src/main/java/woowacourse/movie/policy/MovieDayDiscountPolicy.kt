package woowacourse.movie.policy

import java.time.LocalDateTime

class MovieDayDiscountPolicy : TicketDiscountPolicy {

    override fun discount(price: Int, bookedDateTime: LocalDateTime): Int {
        if (isDiscountable(bookedDateTime)) {
            return (price * DISCOUNTED_RATE).toInt()
        }
        return price
    }

    private fun isDiscountable(bookedDateTime: LocalDateTime): Boolean =
        MOVIE_DAYS.any { it == bookedDateTime.dayOfMonth }

    companion object {
        private const val DISCOUNTED_RATE = 0.9
        private val MOVIE_DAYS = listOf(10, 20, 30)
    }
}
