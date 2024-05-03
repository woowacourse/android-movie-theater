package woowacourse.movie.model

import java.time.LocalTime

class Theater(val id: Long, val name: String, val screens: List<ScreeningMovie>) {
    fun findScreenScheduleListWithMovieId(movieId: Long): List<LocalTime> {
        return findScreenListWithMovieId(movieId)
            .flatMap { it.screenSchedule }
    }

    private fun findScreenListWithMovieId(movieId: Long): List<ScreeningMovie> {
        return screens.filter { it.movieId == movieId }
    }
}
