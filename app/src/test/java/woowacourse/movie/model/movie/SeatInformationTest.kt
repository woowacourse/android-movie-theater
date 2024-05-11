package woowacourse.movie.model.movie

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.model.A1_SEAT
import woowacourse.movie.model.A2_SEAT
import woowacourse.movie.model.B1_SEAT
import woowacourse.movie.model.B2_SEAT
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.SeatInformation

class SeatInformationTest {
    @Test
    fun `좌석을 선택한다`() {
        // given
        val seatInformation = SeatInformation(10, mutableListOf(A1_SEAT))

        // when
        val actual = seatInformation.selectedSeat[0]

        // then
        assertThat(actual).isEqualTo(A1_SEAT)
    }

    @Test
    fun `예매 인원만큼 좌석을 선택한 경우 더이상 선택되지 않는다`() {
        // given
        val seatInformation = SeatInformation(1, mutableListOf(A1_SEAT))

        // when
        seatInformation.addSeat(1, 1)
        val actual = seatInformation.selectedSeat.contains(Seat(1, 1))

        // then=
        assertThat(actual).isFalse()
    }

    @Test
    fun `좌석을 제거한다`() {
        // given
        val seatInformation = SeatInformation(1, mutableListOf(A1_SEAT))

        // when
        seatInformation.removeSeat(0, 0)
        val actual = seatInformation.selectedSeat.contains(Seat(0, 0))

        // then=
        assertThat(actual).isFalse()
    }

    @Test
    fun `선택한 좌석들의 총 가격을 계산한다`() {
        // given
        val selectedSeat = mutableListOf(A1_SEAT, A2_SEAT)
        val seatInformation = SeatInformation(10, selectedSeat)

        // when
        val actual = seatInformation.totalSeatAmount()
        val expected = A1_SEAT.price() + A2_SEAT.price()

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `예매 인원 수 만큼 좌석을 선택한 경우 true를 반환한다`() {
        // given
        val selectedSeat = mutableListOf(A1_SEAT, B1_SEAT)
        val seatInformation = SeatInformation(2, selectedSeat)

        // when
        val actual = seatInformation.checkSelectCompletion()

        // then
        assertThat(actual).isTrue()
    }

    @Test
    fun `예매 인원 수 만큼 좌석을 선택하지 않은 경우 false를 반환한다`() {
        // given
        val selectedSeat = mutableListOf(B1_SEAT, B2_SEAT)
        val seatInformation = SeatInformation(3, selectedSeat)

        // when
        val actual = seatInformation.checkSelectCompletion()

        // then
        assertThat(actual).isFalse()
    }
}
