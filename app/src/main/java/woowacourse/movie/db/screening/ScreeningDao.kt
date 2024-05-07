package woowacourse.movie.db.screening

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID
import java.time.LocalDate

class ScreeningDao {
    private val movies: List<Movie> = ScreeningDatabase.movies

    fun find(movieId: Int): Movie {
        if (movieId == DEFAULT_MOVIE_ID) {
            return Movie(
                -1,
                0,
                "",
                listOf(
                    LocalDate.now(),
                    LocalDate.now(),
                ),
                "",
                "",
            )
        }
        return movies[movieId]
    }

    fun findAll(): List<Movie> = movies
}
