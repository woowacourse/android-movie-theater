package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketInfoDao {
    @Query("SELECT * FROM ticketinfo")
    fun getAll(): List<TicketInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ticketInfo: TicketInfo)

    @Delete
    fun delete(ticketInfo: TicketInfo)
}
