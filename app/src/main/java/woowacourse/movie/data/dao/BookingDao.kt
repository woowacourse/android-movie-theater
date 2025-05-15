package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.BookingInfoEntity

@Dao
interface BookingDao {
    @Query("SELECT * FROM booking_info")
    fun getAll(): List<BookingInfoEntity>

    @Insert
    fun insert(bookingInfo: BookingInfoEntity)
}
