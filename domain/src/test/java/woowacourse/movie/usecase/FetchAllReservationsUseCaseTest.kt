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
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import woowacourse.movie.usecase.FetchScreeningWithIdUseCase

@ExtendWith(MockKExtension::class)
class FetchAllReservationsUseCaseTest {
    @MockK
    private lateinit var fetchScreeningWithIdUseCase: FetchScreeningWithIdUseCase

    @MockK
    private lateinit var reservationRefRepository: ReservationRefRepository

    @InjectMockKs
    private lateinit var fetchAllReservationsUseCase: FetchAllReservationsUseCase

    @Test
    fun `영화 상영 정보를 id를 통해 불러온다`() {
        // given
        every { fetchScreeningWithIdUseCase(any()).getOrNull() } returns Screening.STUB
        every { reservationRefRepository.allReservationRefs() } returns listOf(ReservationRef.STUB)

        // when
        val actual = fetchAllReservationsUseCase().getOrNull()

        // then
        val expected = listOf(Reservation.STUB)
        Assertions.assertThat(actual).isEqualTo(expected)
    }
}
