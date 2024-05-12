package woowacourse.movie.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Screening
import woowacourse.movie.model.ScreeningRef
import woowacourse.movie.model.Theater
import woowacourse.movie.repository.ScreeningRefRepository
import woowacourse.movie.repository.TheaterRepository

@ExtendWith(MockKExtension::class)
class FetchScreeningWithMovieIdAndTheaterIdUseCaseTest {
    @MockK
    private lateinit var everythingRepository: EverythingRepository

    @MockK
    private lateinit var theaterRepository: TheaterRepository

    @MockK
    private lateinit var screeningRefRepository: ScreeningRefRepository

    @InjectMockKs
    private lateinit var fetchScreeningsWithMovieIdAndTheaterIdUseCase: FetchScreeningsWithMovieIdAndTheaterIdUseCase

    @Test
    fun `영화 상영 정보를 id를 통해 불러온다`() {
        // given
        every { everythingRepository.movieById(any()) } returns Movie.STUB_A
        every { theaterRepository.theaterById(any()) } returns Theater.STUB_A
        every {
            screeningRefRepository.screeningRefsByMovieIdAndTheaterId(
                any(),
                any(),
            )
        } returns listOf(ScreeningRef.STUB)

        // when
        val actual = fetchScreeningsWithMovieIdAndTheaterIdUseCase(1, 1).getOrNull()

        // then
        val expected = listOf(Screening.STUB)
        assertThat(actual).isEqualTo(expected)
    }
}
