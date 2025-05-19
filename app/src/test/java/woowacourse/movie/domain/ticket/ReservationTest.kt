package woowacourse.movie.domain.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ReservationTest {
    private lateinit var reservation: Reservation

    @BeforeEach
    fun setUp() {
        reservation =
            Reservation(
                title = "해리 포터와 마법사의 돌",
                count = 2,
                showtime = LocalDateTime.of(2025, 4, 15, 11, 0),
            )
    }

    @Test
    fun `영화 티켓 한 장은 13,000원이다`() {
        assertThat(reservation.price).isEqualTo(26_000)
    }
}
