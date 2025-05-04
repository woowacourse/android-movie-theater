package woowacourse.movie.domain.model.ticketing

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.movie.domain.model.cinema.Seat
import woowacourse.movie.domain.model.cinema.SeatType
import woowacourse.movie.domain.model.reservation.ReservationCount
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.domain.model.ticketing.DiceCinemaPricePolicy
import woowacourse.movie.domain.model.ticketing.TicketMachine
import java.time.LocalDateTime

class TicketMachineTest {
    private val mockPolicy =
        mockk<PricePolicy> {
            every { calculatePrice(SeatType.S_CLASS) } returns 15000
            every { calculatePrice(SeatType.B_CLASS) } returns 12000
            every { calculatePrice(SeatType.A_CLASS) } returns 10000
        }
    private val ticketMachine = TicketMachine(mockPolicy)
    private lateinit var fakeReservationInfo: ReservationInfo
    private lateinit var seats: List<Seat>

    @BeforeEach
    fun setUp() {
        fakeReservationInfo =
            ReservationInfo(
                "해리 포터",
                LocalDateTime.of(2025, 4, 27, 20, 0),
                ReservationCount(3),
            )
        seats =
            listOf(
                Seat(1, 1),
                Seat(3, 1),
                Seat(5, 1),
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
                val expectedTotalPrice = seats.sumOf { mockPolicy.calculatePrice(it.type) }
                assertThat(ticketBundle.price).isEqualTo(expectedTotalPrice)
            },
            { assertThat(ticketBundle.seats).containsExactlyElementsOf(seats) },
        )
    }

    @Test
    fun `총 가격을 계산한다`() {
        val totalPrice = ticketMachine.calculateTotalPrice(seats)
        val expectedTotalPrice = seats.sumOf { mockPolicy.calculatePrice(it.type) }

        assertThat(totalPrice).isEqualTo(expectedTotalPrice)
    }
}
