package woowacourse.movie.db.theater

import woowacourse.movie.model.Theater

class TheaterDao {
    private val theaters: List<Theater> = TheaterDatabase.theaters

    fun findTheaterByMovieId(movieId: Int): List<Theater> {
        return theaters.filter { it.movieId == movieId }
    }

    fun find(theaterId: Int): Theater {
        return theaters[theaterId]
    }

    fun findAll(): List<Theater> = theaters
}
