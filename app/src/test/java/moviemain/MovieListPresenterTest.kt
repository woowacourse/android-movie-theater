package moviemain

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.Schedule
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.moviemain.movielist.MovieListContract
import woowacourse.movie.view.moviemain.movielist.MovieListPresenter
import java.time.LocalDate
import java.time.LocalTime

class MovieListPresenterTest {
    private lateinit var presenter: MovieListContract.Presenter
    private lateinit var view: MovieListContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        val movieRepository = object : MovieRepository {
            override fun findAll(): List<Movie> {
                return listOf(
                    Movie(
                        "해리 포터와 마법사의 돌",
                        LocalDate.of(2024, 4, 1),
                        LocalDate.of(2024, 4, 15),
                        Minute(152),
                        "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                        Schedule(mapOf("선릉 극장" to listOf(LocalTime.of(13, 0)))),
                    ),
                )
            }
        }

        presenter = MovieListPresenter(view, movieRepository)
    }

    @Test
    fun Movie를_Poster가_포함된_UiModel로_변환하여_띄울_수_있다() {
        val slot = slot<List<MovieUiModel>>()
        every { view.showMovieList(capture(slot)) } just runs
        presenter.fetchMovieList()
        val expected = listOf(
            MovieUiModel(
                "해리 포터와 마법사의 돌",
                LocalDate.of(2024, 4, 1),
                LocalDate.of(2024, 4, 15),
                152,
                R.drawable.harry_potter1_poster,
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
                mapOf("선릉 극장" to listOf(LocalTime.of(13, 0))),
            ),
        )
        assertEquals(expected, slot.captured)
        verify { view.showMovieList(expected) }
    }
}
