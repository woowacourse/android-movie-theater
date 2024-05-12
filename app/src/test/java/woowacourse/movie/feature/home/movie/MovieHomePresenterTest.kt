package woowacourse.movie.feature.home.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.movie.MovieRepositoryImpl

class MovieHomePresenterTest {
    private lateinit var view: MovieHomeContract.View
    private lateinit var presenter: MovieHomePresenter
    private val movies = MovieRepositoryImpl.getAllMovies()

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MovieHomePresenter(view)
    }

    @Test
    fun `영화들을 불러온다`() {
        // given
        every { view.displayMovies(any()) } just Runs

        // when
        presenter.loadMovies()

        // then
        verify { view.displayMovies(movies) }
    }
}
