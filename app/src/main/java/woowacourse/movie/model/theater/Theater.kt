package woowacourse.movie.model.theater

import woowacourse.movie.model.database.MovieDao
import woowacourse.movie.model.movie.Movie
import java.time.LocalDate

class Theater(
    val name: String,
    private val movies: List<Movie>,
    private val movieDao: MovieDao,
) {
    fun getTotalTimeSlotCount(movie: Movie): Int {
        if (movie !in movies) {
            return 0
        }
        val endDate = movies.find { it == movie }?.endDate ?: LocalDate.now()
        return movieDao.getTotalTimeSlotCount(this, movie, endDate)
    }
}
