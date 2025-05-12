package woowacourse.movie.presenter.reservationDetails

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.model.ticket.MovieTicket

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(movieTicket: MovieTicket)

    @Query("SELECT * from movieTicket")
    fun findReservations(): List<MovieTicket>

    @Query("DELETE  from movieTicket")
    fun clear()

    fun findReservation(ticketId: Long): MovieTicket? {
        val reservations = findReservations()
        return reservations.find {
            it.ticketId == ticketId
        }
    }
}
