package woowacourse.movie.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationTicketDao {
    @Insert
    fun saveReservationTicket(reservationTicket: ReservationTicketEntity): Long

    @Query("SELECT * FROM reservationTicket")
    fun findAllReservation(): List<ReservationTicketEntity>

    @Query("DELETE FROM reservationTicket")
    fun clearAllReservation()

    @Query("SELECT * FROM reservationTicket WHERE ticketId = :ticketId")
    fun findReservationById(ticketId: Long): ReservationTicketEntity?
}
