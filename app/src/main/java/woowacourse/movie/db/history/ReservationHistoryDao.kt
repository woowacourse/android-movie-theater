package woowacourse.movie.db.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.model.ticket.ReservationTicket

@Dao
interface ReservationHistoryDao {
    @Insert
    fun saveReservationTicket(reservationTicket: ReservationTicket): Long

    @Query("SELECT * FROM reservationTicket")
    fun findReservations(): List<ReservationTicket>

    @Query("SELECT * FROM reservationTicket WHERE ticketId = :ticketId")
    fun findReservationById(ticketId: Long): ReservationTicket?
}
