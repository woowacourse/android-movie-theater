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
    fun insertBookingStatusEntity(bookingStatusEntity: BookingStatusEntity)

    @Delete
    fun deleteBookingStatusEntity(bookingStatusEntity: BookingStatusEntity)
}


