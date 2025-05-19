package woowacourse.movie.domain.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.reservation.PurchaseType
import java.time.LocalDateTime

class TicketHistoryTest {
    private lateinit var ticketHistory: TicketHistory

    @BeforeEach
    fun setUp() {
        ticketHistory =
            TicketHistory(
                title = "해리 포터와 마법사의 돌",
                count = 2,
                showtime = LocalDateTime.of(2025, 4, 15, 11, 0),
                cinemaName = "선릉 극장",
                seats = emptySet(),
                purchaseType = PurchaseType.DEFAULT,
            )
    }

    @Test
    fun `영화 티켓 한 장은 13,000원이다`() {
        assertThat(ticketHistory.price).isEqualTo(26_000)
    }
}
