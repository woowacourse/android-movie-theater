package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingHistoryDao {
    @Query("SELECT * from booking_histories")
    fun getAll(): List<BookingHistory>

    @Insert
    fun insert(vararg bookingHistory: BookingHistory)

    @Delete
    fun delete(bookingHistory: BookingHistory)
}