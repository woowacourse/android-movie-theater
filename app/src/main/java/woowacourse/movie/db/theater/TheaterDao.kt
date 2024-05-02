package woowacourse.movie.db.theater

import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.movie.ScreeningTimes

class TheaterDao {
    private val theaters: List<Theater> = TheaterDatabase.theaters

    fun findScreeningTimes(theaterId: Int): ScreeningTimes {
        return theaters[theaterId].screeningTimes
    }

    fun findTheaterByMovieId(movieId: Int): List<Theater> {
        return theaters.filter { it.movieId == movieId }
    }

    fun find(theaterId: Int): Theater {
        return theaters[theaterId]
    }

    fun findAll(): List<Theater> = theaters
}
