package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations")
    fun getAll(): List<ReservationEntity>

    @Insert
    fun saveReservation(reservationEntity: ReservationEntity)
}
