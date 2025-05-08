package woowacourse.movie.model.reservation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.model.ticket.MovieTicket

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(movieTicket: MovieTicket)

    @Query("SELECT * from movieTicket")
    fun findReservation(): List<MovieTicket>

    @Query("DELETE  from movieTicket")
    fun clear()
}
