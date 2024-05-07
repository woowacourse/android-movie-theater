package woowacourse.movie.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SelectedSeatsTest {
    private lateinit var selectedSeats: SelectedSeats

    @BeforeEach
    fun setUp() {
        selectedSeats = SelectedSeats(MovieTheater.STUB_A, HeadCount(2))
    }

    @Test
    fun `이미 선택된 좌석을 다시 선택하면, 선택이 해제된다`() {
        // when
        selectedSeats = selectedSeats.select(STUB_A_SEAT.row, STUB_A_SEAT.col).value
        assertThat(selectedSeats.selectedSeats).contains(STUB_A_SEAT.copy(state = SeatState.SELECTED))

        // given
        selectedSeats = selectedSeats.select(STUB_A_SEAT.row, STUB_A_SEAT.col).value

        // then
        assertThat(selectedSeats.selectedSeats).doesNotContain(STUB_A_SEAT.copy(state = SeatState.SELECTED))
    }

    @Test
    fun `예매 인원보다 좌석을 더 많이 선택하면 result는 EXCEED가 던져진다`() {
        // when
        selectedSeats = selectedSeats.select(STUB_A_SEAT.row, STUB_A_SEAT.col).value
        selectedSeats = selectedSeats.select(STUB_B_SEAT.row, STUB_B_SEAT.col).value

        // given
        val result = selectedSeats.select(0, 2)

        // then
        assertThat(result).isInstanceOf(SelectResult.Exceed::class.java)
    }

    companion object {
        private val STUB_A_SEAT = Seat(SeatRate.B, 0, 0)
        private val STUB_B_SEAT = Seat(SeatRate.B, 0, 1)
    }
}
