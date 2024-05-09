package woowacourse.movie.db.seats

import woowacourse.movie.model.seats.TheaterSeat

class SeatsDao {
    private val theaterSeats: List<TheaterSeat> = SeatsDatabase.theaterSeats

    fun findAll(): List<TheaterSeat> = theaterSeats
}
