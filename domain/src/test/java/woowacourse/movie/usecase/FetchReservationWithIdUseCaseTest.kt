package woowacourse.movie.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.ReservationRef
import woowacourse.movie.model.Screening
import woowacourse.movie.repository.ReservationRefRepository

@ExtendWith(MockKExtension::class)
class FetchReservationWithIdUseCaseTest {
    @MockK
    private lateinit var fetchScreeningWithIdUseCase: FetchScreeningWithIdUseCase

    @MockK
    private lateinit var reservationRefRepository: ReservationRefRepository

    @InjectMockKs
    private lateinit var fetchReservationWithIdUseCase: FetchReservationWithIdUseCase

    @Test
    fun `영화 상영 정보를 id를 통해 불러온다`() {
        // given
        every { fetchScreeningWithIdUseCase(any()).getOrNull() } returns Screening.STUB
        every { reservationRefRepository.reservationRefById(any()) } returns ReservationRef.STUB

        // when
        val actual = fetchReservationWithIdUseCase(1).getOrNull()

        // then
        val expected = Reservation.STUB
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}
