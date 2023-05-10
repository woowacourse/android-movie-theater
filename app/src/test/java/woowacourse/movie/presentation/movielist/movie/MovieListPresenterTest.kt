package woowacourse.movie.presentation.movielist.movie

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.movieitem.MovieItemData
import woowacourse.movie.presentation.model.MovieModel
import java.time.LocalDate

class MovieListPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var movieItemData: MovieItemData

    @Before
    fun `setUp`() {
        view = mockk()
        movieItemData = mockk()
        presenter = MovieListPresenter(view, movieItemData)
    }

    @Test
    fun `영화 및 광고 아이템을 세팅한다`() {
        // given
        val movieItemSlot = slot<List<MovieItem>>()
        every { view.setMovieItems(capture(movieItemSlot)) } just runs
        every { movieItemData.getMovieItems() } returns listOf(
            MovieItem.Movie(
                MovieModel(
                    1L,
                    "TestTitle",
                    LocalDate.of(2023, 5, 8),
                    LocalDate.of(2023, 5, 15),
                    220,
                    "TestDescription",
                    1,
                    1,
                ),
            ),
        )

        // when
        presenter.setMovieItems()

        // then
        val expected = listOf(
            MovieItem.Movie(
                MovieModel(
                    1L,
                    "TestTitle",
                    LocalDate.of(2023, 5, 8),
                    LocalDate.of(2023, 5, 15),
                    220,
                    "TestDescription",
                    1,
                    1,
                ),
            ),
        )
        val actual = movieItemSlot.captured
        Assert.assertEquals(expected, actual)
    }
}
