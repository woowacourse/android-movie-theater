package woowacourse.movie.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReservationInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReservation(vararg reservationInfoEntity: ReservationInfoEntity)

    @Query("SELECT * FROM reservation")
    fun getAll(): List<ReservationInfoEntity>
}
