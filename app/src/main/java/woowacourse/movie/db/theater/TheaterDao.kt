package woowacourse.movie.db.theater

import woowacourse.movie.model.Theater

class TheaterDao {
    private val theaters: List<Theater> = TheaterDatabase.theaters

    fun find(theaterId: Int): Theater {
        return theaters[theaterId]
    }

    fun findAll(): List<Theater> = theaters
}
