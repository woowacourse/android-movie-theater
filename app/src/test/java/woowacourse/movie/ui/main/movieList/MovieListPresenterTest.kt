package woowacourse.movie.ui.main.movieList

import com.example.domain.repository.AdvRepository
import com.example.domain.repository.MovieRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MovieListPresenterTest {
    @Test
    fun `영화 목록을 가져온다`() {
        // given
        val view = mockk<MovieListContract.View>(relaxed = true)
        val movieRepository = mockk<MovieRepository>(relaxed = true)
        val advRepository = mockk<AdvRepository>(relaxed = true)
        val presenter = MovieListPresenter(view, movieRepository, advRepository)

        // when
        every { movieRepository.allMovies() } returns listOf()
        every { advRepository.allAdv() } returns listOf()

        presenter.setUpMovieList()

        // then
        verify { view.setMovieList(listOf(), listOf()) }
    }
}
