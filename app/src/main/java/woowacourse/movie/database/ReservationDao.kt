package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations")
    fun getAll(): List<ReservationData>

    @Query("SELECT * FROM reservations WHERE id = :id")
    fun selectWithId(id: Long): ReservationData?

    @Query("DELETE FROM reservations WHERE id = :id")
    fun deleteWithId(id: Long)

    @Insert
    fun insert(reservationData: ReservationData): Long

    @Insert
    fun insertAll(vararg reservationData: ReservationData)

    @Delete
    fun delete(reservationData: ReservationData)
}
