package woowacourse.movie.model

import java.time.LocalDateTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

data class Reservation(
    val id: Long,
    val screening: Screening,
    val screenDateTime: LocalDateTime,
    val seats: Seats,
    val cancelDeadLine: Duration = 15.minutes,
) {
    val totalPrice: Price get() = seats.totalPrice

    companion object {
        val STUB =
            Reservation(
                id = 0,
                screening = Screening.STUB_A,
                seats = Seats(listOf(Seat(Tier.A, 1, 1))),
                screenDateTime = LocalDateTime.of(2024, 5, 6, 1, 11, 11),
            )
    }
}
