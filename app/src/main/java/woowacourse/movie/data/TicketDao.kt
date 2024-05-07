package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.Ticket

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets")
    fun getAll(): List<Ticket>

    @Insert
    fun insertAll(vararg ticket: Ticket)

    @Delete
    fun delete(ticket: Ticket)
}
