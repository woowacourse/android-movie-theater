package woowacourse.movie.model

import java.time.LocalTime

class Theater(val id: Long, val name: String, val screens: List<Screen>) {
    fun findScreenScheduleListWithMovieId(movieId: Long): List<LocalTime> {
        return findScreenListWithMovieId(movieId)
            .flatMap { it.screenSchedule }
    }

    private fun findScreenListWithMovieId(movieId: Long): List<Screen> {
        return screens.filter { it.movieId == movieId }
    }
}
