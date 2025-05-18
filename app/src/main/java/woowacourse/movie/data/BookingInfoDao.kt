package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingInfoDao {
    @Query("SELECT * FROM reservation_details")
    fun getAll(): List<BookingInfoEntity>

    @Insert
    fun insertAll(vararg bookingInfo: BookingInfoEntity)

    @Delete
    fun delete(bookingInfo: BookingInfoEntity)
}
