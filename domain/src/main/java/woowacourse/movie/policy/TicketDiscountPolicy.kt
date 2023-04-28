package woowacourse.movie.policy

import java.time.LocalDateTime

interface TicketDiscountPolicy {

    fun discount(price: Int, bookedDateTime: LocalDateTime): Int
}
