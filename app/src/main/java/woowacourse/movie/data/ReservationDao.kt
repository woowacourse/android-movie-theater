package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters

@Dao
@TypeConverters(ReservationConverters::class)
interface ReservationDao {
    @Query("SELECT * FROM reservations")
    fun getAllReservation(): List<ReservationEntity>

    @Insert
    fun saveReservation(reservationEntity: ReservationEntity)
}
