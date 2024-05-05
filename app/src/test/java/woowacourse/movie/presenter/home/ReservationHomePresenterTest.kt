package woowacourse.movie.presenter.home

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.db.advertisement.AdvertisementDatabase
import woowacourse.movie.db.screening.ScreeningDatabase
import woowacourse.movie.model.advertisement.Advertisement
import woowacourse.movie.model.movie.Movie

@ExtendWith(MockKExtension::class)
class ReservationHomePresenterTest {
    @MockK
    private lateinit var view: ReservationHomeContract.View
    private lateinit var presenter: ReservationHomeContract.Presenter
    private lateinit var movies: List<Movie>
    private lateinit var ads: List<Advertisement>

    @BeforeEach
    fun setUp() {
        presenter = ReservationHomePresenter(view)
        movies = ScreeningDatabase.movies
        ads = AdvertisementDatabase.advertisements
    }

    @Test
    fun `영화_목록의_지금_예매_버튼을_누르면_선택된_영화의_ID를_가지고_극장_선택으로_이동한다`() {
        val expectedMovieId = 0

        every { view.navigateToDetail(0) } answers {
            val actualMovieId = arg<Int>(0)
            assertEquals(actualMovieId, expectedMovieId)
        }
        presenter.loadMovie(
            Movie(
                0,
                0,
                "해리포터",
                emptyList(),
                "",
                "",
            ),
        )
        verify { view.navigateToDetail(0) }
    }

    @Test
    fun `홈 화면으로 이동하면 영화와 광고 목록이 화면에 표시되어야 한다`() {
        every { view.showMovieData(movies, ads) } answers {
            arg<List<Movie>>(0).forEachIndexed { index, movie ->
                val actualMovie = movies[index]
                assertEquals(movie.id, actualMovie.id)
            }
            arg<List<Advertisement>>(1).forEachIndexed { index, advertisement ->
                val actualAd = ads[index]
                assertEquals(advertisement.banner, actualAd.banner)
            }
        }
        presenter.loadMovies()
        verify { view.showMovieData(movies, ads) }
    }
}
