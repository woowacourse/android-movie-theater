package woowacourse.movie.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservation")
    fun getAll(): List<ReservationEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg reservation: ReservationEntity)
}
