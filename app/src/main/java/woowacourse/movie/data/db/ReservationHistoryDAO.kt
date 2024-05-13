package woowacourse.movie.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationHistoryDAO {
    @Insert
    fun saveReservationHistory(reservationHistoryEntity: ReservationHistoryEntity): Long

    @Query("SELECT * FROM reservation_history WHERE id = :ticketId")
    fun findReservationHistoryById(ticketId: Long): ReservationHistoryEntity

    @Query("SELECT * from reservation_history")
    fun findReservationHistories(): List<ReservationHistoryEntity>

    @Query("DELETE FROM reservation_history")
    fun clearReservations()
}
