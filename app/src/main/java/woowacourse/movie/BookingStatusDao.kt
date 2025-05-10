package woowacourse.movie

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingStatusDao {
    @Query("SELECT * FROM booking_status")
    fun getAll(): List<BookingStatusEntity>

    @Insert
    fun insert(bookingStatusEntity: BookingStatusEntity)

    @Delete
    fun delete(bookingStatusEntity: BookingStatusEntity)
}


