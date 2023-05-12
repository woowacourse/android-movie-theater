package woowacourse.movie.ui.main.movieList

import android.net.Uri
import com.example.domain.model.Adv
import com.example.domain.model.Movie
import com.example.domain.repository.AdvRepository
import com.example.domain.repository.MovieRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState

class MovieListPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var movieRepository: MovieRepository
    private lateinit var advRepository: AdvRepository
    private lateinit var uri: Uri

    private lateinit var presenter: MovieListPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        movieRepository = mockk(relaxed = true)
        advRepository = mockk(relaxed = true)
        uri = mockk(relaxed = true)

        presenter = MovieListPresenter(view, movieRepository, advRepository)
    }

    @Test
    fun `영화 목록을 가져온다`() {
        // given
        val fakeMovie = getFakeMovie()
        val fakeAdv = getFakeAdv()
        val fakeMovieState = getFakeMovieState()
        val fakeAdvState = getFakeAdvState()

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns uri
        every { movieRepository.allMovies() } returns listOf(fakeMovie)
        every { advRepository.allAdv() } returns listOf(fakeAdv)

        // when
        presenter.setUpMovieList()

        // then
        verify { view.showMovieList(listOf(fakeMovieState), listOf(fakeAdvState)) }
    }

    private fun getFakeMovie(): Movie = Movie(
        imgUri = "",
        title = "fakeTitle",
        startDate = LocalDate.MIN,
        endDate = LocalDate.MIN,
        screeningTimes = listOf(LocalTime.MIN),
        runningTime = 0,
        description = "fakeDescription"
    )

    private fun getFakeMovieState(): MovieState = MovieState(
        imgUri = uri,
        title = "fakeTitle",
        startDate = LocalDate.MIN,
        endDate = LocalDate.MIN,
        screeningTimes = listOf(LocalTime.MIN),
        runningTime = 0,
        description = "fakeDescription"
    )

    private fun getFakeAdvState(): AdvState = AdvState(
        imageUri = uri,
        advDescription = "fakeDescription"
    )

    private fun getFakeAdv(): Adv = Adv(
        imageUrl = "",
        advDescription = "fakeDescription"
    )
}
