package woowacourse.movie.db.theater

import woowacourse.movie.model.theater.Theater
import java.time.LocalTime

class TheaterDao {
    private val theaters: List<Theater> = TheaterDatabase.theaters

    fun findTheaterByMovieId(movieId: Int): List<Theater> {
        return theaters.filter { theater ->
            theater.screeningSchedule.any {
                it.movieId == movieId
            }
        }
    }

    fun findScreeningTimesByMovieId(
        theaterId: Int,
        movieId: Int,
    ): List<LocalTime> {
        val screeningTimes = mutableListOf<LocalTime>()
        screeningTimes.addAll(
            theaters[theaterId].screeningSchedule.filter {
                it.movieId == movieId
            }.map { it.screeningTime },
        )
        return screeningTimes
    }

    fun find(theaterId: Int): Theater {
        return theaters[theaterId]
    }

    fun findAll(): List<Theater> = theaters
}
