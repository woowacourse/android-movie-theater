package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.Reservation

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(reservation: Reservation)

    @Query("SELECT * from reservations")
    fun findReservations(): List<Reservation>
}
