package woowacourse.movie.domain.usecase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.FakeAdRepository
import woowacourse.movie.data.repository.FakeMovieDataSource
import woowacourse.movie.data.repository.FakeScreenDataSource
import woowacourse.movie.domain.model.ScreenAndAd
import woowacourse.movie.domain.model.toScreen

class ScreensAndAdvertisementUseCaseTest {
    private lateinit var screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase

    @BeforeEach
    fun setUp() {
        screensAndAdvertisementUseCase =
            ScreensAndAdvertisementUseCase(
                FakeMovieDataSource(),
                FakeScreenDataSource(),
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
                FakeScreenDataSource.fakeScreen1Data.toScreen(),
                FakeScreenDataSource.fakeScreen2Data.toScreen(),
                FakeScreenDataSource.fakeScreen3Data.toScreen(),
                ScreenAndAd.Advertisement(0),
            )

        assertThat(generatedScreenAndAd).isEqualTo(expected)
    }
}
