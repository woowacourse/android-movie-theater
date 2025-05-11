package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations ORDER BY date ASC, time ASC")
    fun getAll(): List<Reservation>

    @Query("SELECT * FROM reservations WHERE uid = :id")
    fun getById(id: Long): Reservation?

    @Insert
    fun insertAll(vararg users: Reservation)

    @Insert
    fun insert(reservation: Reservation): Long

    @Delete
    fun delete(user: Reservation)
}
