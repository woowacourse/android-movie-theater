package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets")
    fun getAll(): List<TicketEntity>

    @Insert
    fun insertAll(vararg ticket: TicketEntity)

    @Delete
    fun delete(ticket: TicketEntity)
}
