package woowacourse.movie.presentation.movielist

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.model.data.storage.MovieStorage

class MovieListPresenterTest {
    private lateinit var presenter: MovieListPresenter
    private lateinit var view: MovieListContract.View
    private lateinit var movieStorage: MovieStorage

    @Before
    fun initSettingsPresenter() {
        view = mockk()
        movieStorage = DummyMovieStorage()
        presenter = MovieListPresenter(view, movieStorage)
    }

    @Test
    fun `movieList를 조회하여 광고를 더해주는 함수를 호출하면 그에 리사이클러 뷰의 영화목록이 업데이트 된다`() {
        // given
        every { view.updateMovieListView(any()) } just Runs

        // when
        presenter.getMovieItemsWithAds()

        // then
        verify(exactly = 1) { view.updateMovieListView(any()) }
    }
}
