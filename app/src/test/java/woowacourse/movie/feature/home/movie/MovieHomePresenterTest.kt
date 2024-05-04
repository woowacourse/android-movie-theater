package woowacourse.movie.feature.home.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater
import java.time.LocalDate
import java.time.LocalTime

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
