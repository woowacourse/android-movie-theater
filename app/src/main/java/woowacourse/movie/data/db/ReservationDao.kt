package woowacourse.movie.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservation")
    fun getAll(): List<ReservationEntity>

    @Insert
    fun insert(vararg reservation: ReservationEntity)
}
