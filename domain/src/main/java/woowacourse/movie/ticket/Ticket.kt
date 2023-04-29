package woowacourse.movie.ticket

import woowacourse.movie.policy.RunningDiscountPolicy
import java.time.LocalDateTime

data class Ticket(
    val movieId: Long,
    val movieTitle: String,
    val bookedDateTime: LocalDateTime,
    val seat: Seat,
) {
    val price: Int = RunningDiscountPolicy().discount(seat.rank.price, bookedDateTime)
}
