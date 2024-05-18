package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations")
    fun getAll(): List<Reservation>

    @Query("SELECT * FROM reservations WHERE ticketId = :id")
    fun getTicketById(id: Long): Reservation

    @Insert
    fun insert(reservation: Reservation)

    @Query("DELETE FROM reservations WHERE ticketId = :id")
    fun deleteTicketById(id: Long)
}
