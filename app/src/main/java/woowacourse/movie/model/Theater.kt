package woowacourse.movie.model

import java.time.LocalDate

class Theater(
    val name: String,
    private val movies: List<Movie>,
) {
    fun getTotalTimeSlotCount(movie: Movie): Int {
        if (movie !in movies) {
            return 0
        }
        val endDate = movies.find { it == movie }?.endDate ?: LocalDate.now()
        return MovieTime().getTotalTimeSlotCount(this, movie, endDate)
    }
}
