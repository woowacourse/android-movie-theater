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
    fun selectWithId(id: Long): ReservationData

    @Insert
    fun insert(reservationData: ReservationData)

    @Insert
    fun insertAll(vararg reservationData: ReservationData)

    @Delete
    fun delete(reservationData: ReservationData)
}
