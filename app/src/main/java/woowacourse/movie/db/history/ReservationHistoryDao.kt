package woowacourse.movie.db.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.model.ticket.ReservationTicket

@Dao
interface ReservationHistoryDao {
    @Insert
    fun saveReservationTicket(reservationTicket: ReservationTicket)

    @Query("SELECT * FROM reservationTicket")
    fun findReservations(): List<ReservationTicket>

    @Query("DELETE FROM reservationTicket")
    fun clearReservations()
}
