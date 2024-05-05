package woowacourse.movie.presenter.theater

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.theater.Theater
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
class TheaterSelectionPresenterTest {
    @MockK
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionContract.Presenter
    private lateinit var movieTheaters: List<Theater>

    @BeforeEach
    fun setUp() {
        presenter = TheaterSelectionPresenter(view, 0)
        movieTheaters = TheaterDao().findTheaterByMovieId(0)
    }

    @Test
    fun `영화를 선택하면 상영 영화에 해당하는 극장 목록이 표시되어야 한다`() {
        val expectedTheaters = listOf(
            Theater(
                theaterId = 0,
                theaterName = "선릉 극장",
                screeningTimes =
                ScreeningTimes(
                    weekDay =
                    listOf(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 0),
                        LocalTime.of(13, 0),
                    ),
                    weekEnd =
                    listOf(
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                    ),
                ),
                movieId = 0,
            ),
            Theater(
                theaterId = 5,
                theaterName = "강남 극장",
                screeningTimes =
                ScreeningTimes(
                    weekDay =
                    listOf(
                        LocalTime.of(11, 0),
                        LocalTime.of(13, 0),
                    ),
                    weekEnd =
                    listOf(
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                    ),
                ),
                movieId = 0,
            ),
            Theater(
                theaterId = 6,
                theaterName = "잠실 극장",
                screeningTimes =
                ScreeningTimes(
                    weekDay =
                    listOf(
                        LocalTime.of(9, 0),
                    ),
                    weekEnd =
                    listOf(
                        LocalTime.of(14, 0),
                    ),
                ),
                movieId = 0,
            ),
        )
        every { view.showTheaters(movieTheaters) } answers {
            val actualTheaters = arg<List<Theater>>(0)
            actualTheaters.forEachIndexed { index, theater ->
                assertEquals(theater.theaterId, expectedTheaters[index].theaterId)
            }
        }
        presenter.loadTheaters(0)
        verify { view.showTheaters(movieTheaters) }
    }

    @Test
    fun `극장_목록에서_극장을_선택하면_선택된_극장_ID와_영화_ID를_가지고_예매_화면으로_이동한다`() {
        val expectedMovieId = 0
        val expectedTheaterId = 0

        every { view.navigateToDetail(0,0) } answers {
            val actualMovieId = arg<Int>(0)
            val actualTheaterId = arg<Int>(0)
            assertEquals(expectedMovieId,actualMovieId)
            assertEquals(expectedTheaterId,actualTheaterId)
        }
        presenter.loadTheater(theaterId = 0)
        verify { view.navigateToDetail(0, 0) }
    }
}
