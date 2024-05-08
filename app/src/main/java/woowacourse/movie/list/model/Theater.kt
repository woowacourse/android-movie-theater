package woowacourse.movie.list.model

import java.time.LocalTime

data class Theater(
    val name: String,
    val screeningMovies: Map<Movie, List<LocalTime>>,
    val id: Long,
) {
    fun getCount(movieId: Long): Int {
        val movie = screeningMovies.filter { it.key.id == movieId }
        val localTimes = movie.values.firstOrNull()
        return localTimes?.size ?: 0
    }

    fun getScreeningTimes(movieId: Long): List<LocalTime> {
        val movie = screeningMovies.filter { it.key.id == movieId }
        return movie.values.firstOrNull() ?: emptyList()
    }
}
