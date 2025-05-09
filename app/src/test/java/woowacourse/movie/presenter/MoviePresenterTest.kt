package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.database.DummyMovieDao
import woowacourse.movie.view.main.home.MovieContract
import woowacourse.movie.view.main.home.MoviePresenter

class MoviePresenterTest {
    private lateinit var presenter: MovieContract.Presenter
    private lateinit var view: MovieContract.View

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MoviePresenter(view, DummyMovieDao)
    }

    @Test
    fun `영화 목록을 불러온다`() {
        // given
        every { view.showMovies(any()) } just Runs

        // when: 영화 목록을 조회하면
        presenter.fetchMovies()

        // then: 뷰에 반영된다.
        verify { view.showMovies(any()) }
    }
}
