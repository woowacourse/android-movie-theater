package woowacourse.movie.repository

import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.ScreeningInfo
import woowacourse.movie.presentation.repository.MovieRepository
import java.time.LocalDate

object MovieRepositoryImpl : MovieRepository {
    private var movies: List<Movie> = listOf()
    private val dummyMovie: Movie =
        Movie(
            movieId = 0,
            posterImageId = R.drawable.harrypotter_poster,
            title = "해리 포터와 마법사의 돌",
            screeningInfo =
                ScreeningInfo(
                    startDate = LocalDate.of(2024, 4, 1),
                    endDate = LocalDate.of(2024, 10, 30),
                    runningTime = 148,
                ),
            summary =
                "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, " +
                    "영국과 미국 합작, 판타지 영화이다. " +
                    "해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. " +
                    "크리스 콜럼버스가 감독을 맡았다.",
        )
    private val defaultMovie: Movie =
        Movie(
            movieId = -1,
            posterImageId = R.drawable.img_noimg,
            title = "",
            screeningInfo =
                ScreeningInfo(
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now(),
                    runningTime = 0,
                ),
            summary = "",
        )
    private const val DATE_TERM: Long = 1
    private const val END_TIME = 24
    private const val TIME_TERM = 2

    override fun createMovieList(): List<Movie> {
        movies = createDummyMovies()
        return movies
    }

    override fun findMovieById(movieId: Int): Movie {
        return movies.find { movie ->
            movie.movieId == movieId
        } ?: defaultMovie
    }

    private fun createDummyMovies(): List<Movie> {
        return List(size = 10) { idx ->
            dummyMovie.copy(
                movieId = idx,
                title = dummyMovie.title + " $idx",
            )
        }
    }
}
