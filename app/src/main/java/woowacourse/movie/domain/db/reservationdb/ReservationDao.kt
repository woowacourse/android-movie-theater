package woowacourse.movie.domain.db.reservationdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservations")
    fun getAll(): List<ReservationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reservationEntity: ReservationEntity): Long

    @Delete
    fun delete(reservations: ReservationEntity)

    @Query("SELECT * FROM reservations WHERE uid = :mId")
    fun getData(mId: Long): ReservationEntity?
}
