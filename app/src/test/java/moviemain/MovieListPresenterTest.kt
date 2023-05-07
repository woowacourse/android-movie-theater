package moviemain

import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.movie.Minute
import woowacourse.movie.domain.movie.Movie
import woowacourse.movie.domain.movie.Schedule
import woowacourse.movie.domain.repository.MoviePosterRepository
import woowacourse.movie.domain.repository.MovieRepository
import woowacourse.movie.view.moviemain.movielist.MovieListContract
import woowacourse.movie.view.moviemain.movielist.MovieListPresenter
import java.time.LocalDate
import java.time.LocalTime

class MovieListPresenterTest {
    private lateinit var presenter: MovieListContract.Presenter

    @Before
    fun setUp() {
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
        val posterRepository = object : MoviePosterRepository {
            override fun findPoster(title: String): Int {
                return R.drawable.harry_potter1_poster
            }
        }
        presenter = MovieListPresenter(movieRepository, posterRepository)
    }

    @Test
    fun Movie를_Poster가_포함된_UiModel로_변환할_수_있다() {
        val movieUiModels = presenter.getMovieListData()
        assertTrue(movieUiModels[0].posterResourceId == R.drawable.harry_potter1_poster)
    }
}
