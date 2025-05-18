package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingInfoDao {
    @Query("SELECT * FROM reservation_details")
    fun getAll(): List<BookingWithSeatsEntity>

    @Insert
    fun insertBookingInfo(booking: BookingInfoEntity): Long

    @Insert
    fun insertSeats(seats: List<BookingSeatEntity>)

    @Delete
    fun deleteBookingInfo(booking: BookingInfoEntity)

    @Delete
    fun deleteSeats(seats: List<BookingSeatEntity>)
}
