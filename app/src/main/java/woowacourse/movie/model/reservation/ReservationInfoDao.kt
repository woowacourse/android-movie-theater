package woowacourse.movie.model.reservation

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReservationInfoDao {
    @Query("SELECT * FROM reservationsInfo")
    fun getAllReservations(): List<ReservationInfo>

    @Query("SELECT * FROM reservationsInfo WHERE id = :id")
    fun getReservationById(id: Int): ReservationInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReservation(reservation: ReservationInfo): Long

    @Update
    fun updateReservation(reservation: ReservationInfo)

    @Delete
    fun deleteReservation(reservation: ReservationInfo)
}
