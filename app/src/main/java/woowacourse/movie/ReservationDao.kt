package woowacourse.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert
    fun addReservation(reservation: ReservationEntity)

    @Query("SELECT * from reservation")
    fun findReservations(): List<ReservationEntity>
}
