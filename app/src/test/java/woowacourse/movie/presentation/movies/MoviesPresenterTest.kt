package woowacourse.movie.presentation.movies

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.movie.Movie
import java.time.LocalDate

class MoviesPresenterTest {
    private lateinit var view: MoviesContract.View
    private lateinit var movieData: MovieData
    private lateinit var presenter: MoviesContract.Presenter

    private val testMovies =
        listOf(
            Movie(
                "test",
                LocalDate.of(2025, 4, 24),
                LocalDate.of(2025, 4, 30),
                100,
            ),
        )

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        movieData = mockk()
        presenter = MoviesPresenter(view, movieData)
    }

    @Test
    fun `영화 목록을 가져와서 화면에 출력한다`() {
        // Given
        every { movieData.getData() } returns testMovies

        // When
        presenter.onViewCreated()

        // Then
        verify { movieData.getData() }
        verify { view.showMovies(testMovies) }
    }

    @Test
    fun `버튼을 누르면 예매 화면으로 이동한다`() {
        // Given
        val movie = testMovies[0]

        // When
        presenter.onMovieClicked(movie)

        // Then
        verify { view.showTheaterSelectDialog(movie) }
    }
}
