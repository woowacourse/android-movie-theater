package woowacourse.movie.presentation.movielist.cinema

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.cinema.CinemaData
import woowacourse.movie.domain.model.tools.cinema.Cinema
import woowacourse.movie.domain.model.tools.cinema.MovieTimes
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.MovieModel
import java.time.LocalDate

class CinemaPresenterTest {
    private lateinit var view: CinemaContract.View
    private lateinit var presenter: CinemaContract.Presenter
    private lateinit var cinemaData: CinemaData

    @Before
    fun `setUp`() {
        view = mockk()
        cinemaData = mockk()
        // FakeMovieData 사용
        presenter = CinemaPresenter(view, cinemaData)
    }

    @Test
    fun `극장을 목록을 세팅한다`() {
        // given
        val cinemaModelSlot = slot<List<CinemaModel>>()
        val movieModel = MovieModel(
            1L,
            "TestTitle",
            LocalDate.of(2023, 5, 8),
            LocalDate.of(2023, 5, 15),
            220,
            "TestDescription",
            1,
            1,
        )
        every { cinemaData.getCinemas() } returns listOf(
            Cinema.of(
                "선릉",
                1L to MovieTimes.of(9, 15, 2),
                2L to MovieTimes.of(12, 22, 1),
                3L to MovieTimes.of(15, 23, 2),
            ),
            Cinema.of(
                "잠실",
                2L to MovieTimes.of(14, 18, 1),
                3L to MovieTimes.of(15, 23, 2),
            ),
        )
        every { view.setCinemaModels(capture(cinemaModelSlot)) } just runs

        // when
        presenter.setCinemaModels(movieModel)

        // then
        val expected = listOf(CinemaModel("선릉", 1L, MovieTimes.of(9, 15, 2).times))
        val actual = cinemaModelSlot.captured
        assertEquals(expected, actual)
    }
}
