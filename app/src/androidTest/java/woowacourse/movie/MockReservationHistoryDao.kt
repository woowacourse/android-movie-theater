package woowacourse.movie

import androidx.room.Dao
import androidx.room.Query
import woowacourse.movie.db.history.ReservationHistoryDao
import woowacourse.movie.model.ticket.ReservationTicket

@Dao
interface MockReservationHistoryDao : ReservationHistoryDao {
    override fun saveReservationTicket(reservationTicket: ReservationTicket): Long

    override fun findReservations(): List<ReservationTicket>

    override fun findReservationById(ticketId: Long): ReservationTicket?

    @Query("DELETE FROM reservationTicket")
    fun clearReservations()
}
