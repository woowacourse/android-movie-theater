package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert
    fun insert(ticket: TicketEntity)

    @Query("SELECT * FROM tickets")
    fun getAll(): List<TicketEntity>

    @Query("DELETE FROM tickets")
    fun deleteAll()
}
