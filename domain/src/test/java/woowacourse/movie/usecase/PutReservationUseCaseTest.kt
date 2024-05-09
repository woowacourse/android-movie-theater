package woowacourse.movie.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Seats
import woowacourse.movie.repository.ReservationRefRepository

@ExtendWith(MockKExtension::class)
class PutReservationUseCaseTest {
    @MockK
    private lateinit var reservationRefRepository: ReservationRefRepository

    @InjectMockKs
    private lateinit var putReservationUseCase: PutReservationUseCase

    @Test
    fun `영화를 예매한다`() {
        // given
        val screeningIdSlot = slot<Long>()
        val seatsSlot = slot<String>()
        every { reservationRefRepository.makeReservationRef(capture(screeningIdSlot), capture(seatsSlot)) } returns 1

        // when
        val seats = Seats.STUB
        putReservationUseCase(1, seats)

        // then
        val actual = seatsSlot.captured
        val expected = Seats.STUB.toString()
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}
