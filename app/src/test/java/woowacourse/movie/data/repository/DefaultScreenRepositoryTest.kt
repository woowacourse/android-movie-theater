package woowacourse.movie.data.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.ScreenAndAd
import java.time.LocalDate

class DefaultScreenRepositoryTest {
    private lateinit var movieRepository: DefaultMovieRepository
    private lateinit var screenRepository: DefaultScreenRepository
    private lateinit var screenDataSource: FakeScreenDataSource
    private lateinit var movieDataSource: FakeMovieDataSource

    @BeforeEach
    fun setUp() {
        movieDataSource = FakeMovieDataSource()
        screenDataSource = FakeScreenDataSource()
        movieRepository = DefaultMovieRepository(movieDataSource)
        screenRepository = DefaultScreenRepository(screenDataSource, movieRepository)
    }

    @Test
    fun loadScreen() {
        val screen = screenRepository.loadScreen(1)

        assertThat(screen).isEqualTo(
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
                dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
            ),
        )
    }

    @Test
    fun loadAllScreens() {
        val screens = screenRepository.loadAllScreens()

        assertThat(screens).isEqualTo(
            listOf(
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
                    dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
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
                    dateRange = DateRange(LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 4)),
                ),
                ScreenAndAd.Screen(
                    id = 2,
                    movie =
                        Movie(
                            id = 3,
                            title = "title3",
                            runningTime = 3,
                            description = "description3",
                            poster = Image.StringImage("3"),
                        ),
                    dateRange =
                        DateRange(
                            LocalDate.of(2024, 3, 3),
                            LocalDate.of(2024, 3, 5),
                        ),
                ),
            ),
        )
    }
}
