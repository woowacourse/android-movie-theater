package woowacourse.movie.feature.home.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.movie.MovieRepository

class MovieHomePresenterTest {
    private lateinit var view: MovieHomeContract.View
    private lateinit var presenter: MovieHomePresenter
    private val movies = MovieRepository.getAllMovies()

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MovieHomePresenter(view)
    }

    @Test
    fun `loadMovies를 호출하면 view에서 영화 리스트를 보여준다`() {
        // Given
        every { view.displayMovies(any()) } just Runs

        // When
        presenter.loadMovies()

        // Then
        verify { view.displayMovies(movies) }
    }
}
