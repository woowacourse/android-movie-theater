package woowacourse.movie.view.moviemain.movielist

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.Minute
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Screening
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieListModel
import java.time.LocalDate

class MovieListPresenterTest {
    private lateinit var movieListPresenter: MovieListPresenter
    private lateinit var view: MovieListContract.View
    private lateinit var movieRepository: MovieRepository
    private lateinit var theaterRepository: TheaterRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        movieRepository = mockk(relaxed = true)
        theaterRepository = mockk(relaxed = true)
        movieListPresenter = MovieListPresenter(view, movieRepository, theaterRepository)
    }

    @Test
    fun `리포지토리에서 영화 목록 받아와 뷰에 띄운다`() {
        val movieSlot = slot<List<MovieListModel>>()
        every { view.showMovieList(capture(movieSlot)) } returns Unit
        every { movieRepository.findAll() } returns fakeMovies()

        movieListPresenter.loadMovieList()

        val expected = fakeMovies().map { it.toUiModel() }
        val actual = movieSlot.captured

        assertEquals(expected, actual)
        verify { view.showMovieList(expected) }
    }

    @Test
    fun `영화가 클릭되면 극장 선택 Bottom Sheet Dialog가 열린다`() {
        val movie = fakeMovie().toUiModel()
        every { theaterRepository.findTheaterByMovieId(movie.id) } returns fakeTheaters()

        movieListPresenter.decideNextAction(movie)

        verify { view.showTheaterList(any(), any()) }
    }

    @Test
    fun `광고가 클릭되면 광고 화면을 연다`() {
        val ad = fakeAd()

        movieListPresenter.decideNextAction(ad)

        verify { view.toAdScreen(ad) }
    }

    private fun fakeMovies() = listOf<Movie>(
        fakeMovie()
    )

    private fun fakeMovie() = Movie(
        1,
        "해리 포터와 마법사의 돌",
        LocalDate.of(2024, 3, 1),
        LocalDate.of(2024, 3, 31),
        Minute(152),
        R.drawable.harry_potter1_poster,
        "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
    )

    private fun fakeAd() = MovieListModel.MovieAdModel(
        R.drawable.woowacourse_banner,
        "https://woowacourse.github.io/"
    )

    private fun fakeTheaters() = listOf<Theater>(
        Theater(
            "선릉 극장",
            listOf(
                Screening(1, listOf(0, 1, 2, 5, 7)),
                Screening(2, listOf(2, 3, 4, 5)),
                Screening(3, listOf(3)),
                Screening(4, listOf(5, 6))
            )
        )
    )
}
