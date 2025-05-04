package woowacourse.movie.domain.model.reservation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.domain.model.cinema.Seat
import java.time.LocalDateTime

class ReservationInfoTest {
    private lateinit var fakeReservationInfo: ReservationInfo

    @BeforeEach
    fun setUp() {
        fakeReservationInfo =
            ReservationInfo(
                "해리 포터와 마법사의 돌",
                LocalDateTime.of(2025, 4, 30, 20, 0),
                ReservationCount(2),
            )
    }

    @Test
    fun `예약 좌석을 추가할 수 있다`() {
        val seat = Seat(0, 1)
        fakeReservationInfo.addSeat(seat)
        assertThat(fakeReservationInfo.seats).contains(seat)
    }

    @Test
    fun `예약 좌석을 제거할 수 있다`() {
        val seat = Seat(0, 1)
        fakeReservationInfo.addSeat(seat)
        fakeReservationInfo.removeSeat(seat)
        assertThat(fakeReservationInfo.seats).doesNotContain(seat)
    }

    @Test
    fun `예매 인원 보다 더 많은 좌석을 선택하면 예외가 발생한다`() {
        val seat = Seat(0, 1)
        fakeReservationInfo.addSeat(seat)
        fakeReservationInfo.addSeat(seat.copy(1))

        assertThrows<IllegalArgumentException> {
            fakeReservationInfo.addSeat(seat.copy(2))
        }
    }

    @Test
    fun `모든 인원에 대해 좌석을 선택해야 발권할 수 있다`() {
        fakeReservationInfo.addSeat(Seat(0, 1))
        fakeReservationInfo.addSeat(Seat(0, 2))
        assertThat(fakeReservationInfo.canPublish()).isTrue()
    }

    @Test
    fun `모든 인원에 대해 좌석을 선택하지 않으면 발권할 수 없다`() {
        assertThat(fakeReservationInfo.canPublish()).isFalse()
    }

    @Test
    fun `특정 좌석 선택 여부를 알 수 있다`() {
        val seat = Seat(0, 1)
        fakeReservationInfo.addSeat(seat)
        val result = fakeReservationInfo.hasSeat(seat)

        assertThat(result).isTrue()
    }
}
