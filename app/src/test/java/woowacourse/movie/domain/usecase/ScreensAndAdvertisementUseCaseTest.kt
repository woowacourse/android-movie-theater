package woowacourse.movie.domain.usecase

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.DefaultAdvertisementRepository
import woowacourse.movie.data.repository.DefaultMovieRepository
import woowacourse.movie.data.repository.DefaultScreenRepository
import woowacourse.movie.data.repository.FakeAdvertisementDataSource
import woowacourse.movie.data.repository.FakeMovieDataSource
import woowacourse.movie.data.repository.FakeScreenDataSource
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.ScreenAndAd
import java.time.LocalDate

class ScreensAndAdvertisementUseCaseTest {
    private lateinit var screensAndAdvertisementUseCase: ScreensAndAdvertisementUseCase

    @BeforeEach
    fun setUp() {
        screensAndAdvertisementUseCase =
            ScreensAndAdvertisementUseCase(
                screenRepository =
                    DefaultScreenRepository(
                        FakeScreenDataSource(),
                        DefaultMovieRepository(FakeMovieDataSource()),
                    ),
                advertisementRepository = DefaultAdvertisementRepository(FakeAdvertisementDataSource()),
            )
    }

    @Test
    fun `상영과 광고 목록을 생성한다`() {
        // given & when
        val generatedScreenAndAd = screensAndAdvertisementUseCase.generatedScreenAndAdvertisement()

        // then
        val expected =
            listOf<ScreenAndAd>(
                ScreenAndAd.Screen(
                    id = 1,
                    movie =
                        Movie(
                            id = 1,
                            title = "title1",
                            runningTime = 1,
                            description = "description1",
                            poster = Image.StringImage("1"),
                        ),
                    DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
                ),
                ScreenAndAd.Screen(
                    id = 2,
                    movie =
                        Movie(
                            id = 2,
                            title = "title2",
                            runningTime = 2,
                            description = "description2",
                            poster = Image.StringImage("2"),
                        ),
                    DateRange(LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 4)),
                ),
                ScreenAndAd.Screen(
                    id = 3,
                    movie =
                        Movie(
                            id = 3,
                            title = "title3",
                            runningTime = 3,
                            description = "description3",
                            poster = Image.StringImage("3"),
                        ),
                    DateRange(LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 5)),
                ),
                ScreenAndAd.Advertisement(
                    id = 0,
                    content = "우아한테크코스",
                    image = Image.DrawableImage(1),
                ),
            )

        assertThat(generatedScreenAndAd).isEqualTo(expected)
    }
}
