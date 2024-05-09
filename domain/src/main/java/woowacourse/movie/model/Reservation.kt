package woowacourse.movie.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class Reservation(
    val id: Long,
    val screening: Screening,
    val seats: Seats,
    val cancelDeadLine: Duration = 15.minutes,
) {
    val totalPrice: Price get() = seats.totalPrice

    companion object {
        val STUB =
            Reservation(
                id = 1,
                screening = Screening.STUB,
                seats = Seats(listOf(Seat(Tier.A, 1, 1))),
            )
    }
}
