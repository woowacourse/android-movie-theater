package woowacourse.movie.movieList

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.AdModel
import woowacourse.movie.model.MovieModel
import woowacourse.movie.view.movieList.MovieListContract
import woowacourse.movie.view.movieList.MovieListPresenter
import java.time.LocalDate

class MovieListPresenterTest {
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var view: MovieListContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieListPresenter(view)
    }

    @Test
    fun `영화 광고 정보를 어댑터에 설정한다`() {
        // given
        val slotMovie = slot<List<MovieModel>>()
        val slotAd = slot<List<AdModel>>()
        presenter.setupMovieList(listOf(dummyMovie), listOf(dummyAd))
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
            "모든 과거를 잊고 서로에게 더 깊게 빠져든 ‘크리스찬 그레이’와 ‘아나스타샤’. 그레이의 독특한 취향으로 시작된 이 비밀스러운 관계는 더 큰 자극을 원하는 아나스타샤로 인해 역전되고, 마침내 그녀의 본능이 지배하는 마지막 절정의 순간을 맞이하게 되는데…"
        )

        private val dummyAd = AdModel(
            0,
            ""
        )
    }
}
