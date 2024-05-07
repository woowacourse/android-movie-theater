package woowacourse.movie.list.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.list.contract.MovieListContract
import woowacourse.movie.list.model.Movie

class MovieListPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var presenter: MovieListContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<MovieListContract.View>()
        presenter = MovieListPresenter(view)
    }

    @Test
    fun `영화 정보를 목록에 띄워야 한다`() {
        every { view.showMoviesInfo() } just runs
        // when
        presenter.setMoviesInfo()
        // then
        verify { view.showMoviesInfo() }
    }

    @Test
    fun `영화 개수는 9개여야 한다(광고 2개 포함)`() {
        // given
        val theaterContentSlot = slot<List<Movie>>()
        every { view.makeMovieListAdapter(capture(theaterContentSlot)) } just runs
        // when
        presenter.setMovieListAdapter()
        // then
        assertThat(theaterContentSlot.captured.size).isEqualTo(9)
    }
}
