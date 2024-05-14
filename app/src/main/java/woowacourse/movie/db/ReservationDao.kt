package woowacourse.movie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(reservation: ReservationEntity): Long

    @Query("SELECT * FROM reservations")
    fun getAllReservation(): List<ReservationEntity>

    @Query("DELETE FROM reservations")
    fun deleteAll()

    @Query("SELECT * FROM reservations WHERE uid == :id")
    fun findReservationById(id: Long): ReservationEntity
}
