package woowacourse.movie.db

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface ReservationDao {
    @Insert
    fun saveReservation(reservation: ReservationEntity)
}
