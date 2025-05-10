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
    suspend fun insertAll(vararg users: BookingHistoryDetails)

    @Delete
    fun delete(user: BookingHistoryDetails)
}
