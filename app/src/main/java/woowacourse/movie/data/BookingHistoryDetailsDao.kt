package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingHistoryDetailsDao {
    @Query("SELECT * FROM reservation_details")
    fun getAll(): List<BookingHistoryDetails>

    @Insert
    fun insertAll(vararg bookingHistoryDetails: BookingHistoryDetails)

    @Delete
    fun delete(bookingHistoryDetails: BookingHistoryDetails)
}
