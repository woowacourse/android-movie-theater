package woowacourse.movie.ui.main.movieList

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository

class MovieListPresenterTest {
    @Test
    fun `영화 목록을 가져온다`() {
        // given
        val movieRepository: MovieRepository = mockk(relaxed = true)
        val presenter = MovieListPresenter(movieRepository = movieRepository)
        every { movieRepository.allMovies() } returns listOf()

        // when
        val movieList = presenter.getMovieList()

        // then
        assert(movieList.isEmpty())
        verify { movieRepository.allMovies() }
    }

    @Test
    fun `광고 목록을 가져온다`() {
        // given
        val advRepository: AdvRepository = mockk(relaxed = true)
        val presenter = MovieListPresenter(advRepository = advRepository)
        every { advRepository.allAdv() } returns listOf()

        // when
        val advList = presenter.getAdvList()

        // then
        assert(advList.isEmpty())
        verify { advRepository.allAdv() }
    }
}
