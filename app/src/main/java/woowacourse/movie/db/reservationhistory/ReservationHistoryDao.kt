package woowacourse.movie.db.reservationhistory

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ReservationHistoryDao {
    @Query("SELECT * FROM reservationHistory")
    fun getAll(): List<ReservationHistory>

    @Query("SELECT * FROM reservationHistory WHERE id = :id")
    fun getById(id: Long): ReservationHistory

    @Upsert
    fun upsert(reservationHistory: ReservationHistory): Long
}
