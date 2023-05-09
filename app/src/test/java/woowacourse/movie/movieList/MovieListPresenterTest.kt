package woowacourse.movie.movieList

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.entity.Ads
import woowacourse.movie.entity.Movies
import woowacourse.movie.model.AdModel
import woowacourse.movie.model.MovieModel
import woowacourse.movie.model.TheaterModel
import woowacourse.movie.view.movieList.MovieListContract
import woowacourse.movie.view.movieList.MovieListPresenter
import java.time.LocalDate
import java.time.LocalTime

class MovieListPresenterTest {
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var view: MovieListContract.View
    private lateinit var movies: Movies
    private lateinit var ads: Ads

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        movies = mockk()
        ads = mockk()
        presenter = MovieListPresenter(view, movies, ads)
    }

    @Test
    fun `영화 광고 정보를 어댑터에 설정한다`() {
        // given
        every { movies.getAll() } returns listOf(dummyMovie)
        every { ads.getAll() } returns listOf(dummyAd)

        val slotMovie = slot<List<MovieModel>>()
        val slotAd = slot<List<AdModel>>()
        justRun { view.setMovieList(capture(slotMovie), capture(slotAd)) }

        // when
        presenter.loadMovieList()

        // then
        val actualMovie = slotMovie.captured
        val actualAd = slotAd.captured
        val expectedMovie = listOf(dummyMovie)
        val expectedAd = listOf(dummyAd)
        assertEquals(expectedMovie, actualMovie)
        assertEquals(expectedAd, actualAd)
        verify { view.setMovieList(actualMovie, actualAd) }
    }

    companion object {
        private val dummyMovie = MovieModel(
            0,
            "그레이의 50가지 그림자 1",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 5, 10),
            105,
            "모든 과거를 잊고 서로에게 더 깊게 빠져든 ‘크리스찬 그레이’와 ‘아나스타샤’. 그레이의 독특한 취향으로 시작된 이 비밀스러운 관계는 더 큰 자극을 원하는 아나스타샤로 인해 역전되고, 마침내 그녀의 본능이 지배하는 마지막 절정의 순간을 맞이하게 되는데…",
            listOf(
                TheaterModel("선릉 극장", listOf(LocalTime.of(11, 0)))
            )
        )

        private val dummyAd = AdModel(
            0,
            "https://woowacourse.github.io/"
        )
    }
}
