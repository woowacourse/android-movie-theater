package woowacourse.movie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(reservation: ReservationEntity)

    @Query("SELECT * FROM reservations")
    fun getAllReservation(): List<ReservationEntity>
}
