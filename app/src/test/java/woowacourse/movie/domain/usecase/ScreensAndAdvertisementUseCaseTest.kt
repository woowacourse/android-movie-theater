package woowacourse.movie.domain.usecase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.ScreenAndAd
import woowacourse.movie.domain.model.toScreen
import woowacourse.movie.domain.repository.FakeAdRepository
import woowacourse.movie.domain.repository.FakeMovieRepository
import woowacourse.movie.domain.repository.FakeScreenRepository

class ScreensAndAdvertisementUseCaseTest {
    private lateinit var screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase

    @BeforeEach
    fun setUp() {
        screensAndAdvertisementUseCase =
            ScreensAndAdvertisementUseCase(
                FakeMovieRepository(),
                FakeScreenRepository(),
                FakeAdRepository(),
            )
    }

    @Test
    fun `상영과 광고 목록을 생성한다`() {
        // given & when
        val generatedScreenAndAd = screensAndAdvertisementUseCase.generated2()

        // then
        val expected =
            listOf<ScreenAndAd>(
                FakeScreenRepository.fakeScreen1Data.toScreen(),
                FakeScreenRepository.fakeScreen2Data.toScreen(),
                FakeScreenRepository.fakeScreen3Data.toScreen(),
                ScreenAndAd.Advertisement(0),
            )

        assertThat(generatedScreenAndAd).isEqualTo(expected)
    }
}
