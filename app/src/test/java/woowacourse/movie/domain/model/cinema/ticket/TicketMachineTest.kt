package woowacourse.movie.domain.model.cinema.ticket

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.movie.domain.model.cinema.DiceCinemaPricePolicy
import woowacourse.movie.domain.model.cinema.screen.Seat
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationInfo
import java.time.LocalDateTime

class TicketMachineTest {
    private val policy = DiceCinemaPricePolicy()
    private val ticketMachine = TicketMachine(policy)
    private val seats =
        listOf(
            Seat(1, 1),
            Seat(1, 2),
        )
    private lateinit var fakeReservationInfo: ReservationInfo

    @BeforeEach
    fun setUp() {
        fakeReservationInfo =
            ReservationInfo(
                "해리 포터",
                LocalDateTime.of(2025, 4, 27, 20, 0),
                ReservationCount(2),
            )
        seats.forEach { fakeReservationInfo.updateSeats(it) }
    }

    @Test
    fun `티켓 출판시 티켓 모음이 올바르게 생성된다`() {
        val ticketBundle = ticketMachine.publishTickets(fakeReservationInfo, "선릉 극장")

        assertAll(
            { assertThat(ticketBundle.title).isEqualTo(fakeReservationInfo.title) },
            { assertThat(ticketBundle.count).isEqualTo(seats.size) },
            { assertThat(ticketBundle.reservationDateTime).isEqualTo(fakeReservationInfo.reservationDateTime) },
            {
                val expectedTotalPrice = seats.sumOf { policy.calculatePrice(it.type) }
                assertThat(ticketBundle.price).isEqualTo(expectedTotalPrice)
            },
            { assertThat(ticketBundle.seats).containsExactlyElementsOf(seats) },
        )
    }
}
