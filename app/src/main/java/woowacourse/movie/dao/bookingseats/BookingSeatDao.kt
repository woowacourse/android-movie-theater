package woowacourse.movie.dao.bookingseats

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookingSeatDao {
    @Query("SELECT * FROM booking_seat")
    fun getAll(): List<BookingSeatEntity>

    @Insert
    fun insert(bookingSeatEntity: BookingSeatEntity)

    @Delete
    fun delete(bookingSeatEntity: BookingSeatEntity)
}
