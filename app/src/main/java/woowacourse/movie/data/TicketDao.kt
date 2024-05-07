package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.Ticket

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets")
    fun getAll(): List<Ticket>

    @Query("SELECT * FROM tickets WHERE id = :id")
    fun find(id: Long): Ticket

    @Insert
    fun insert(ticket: Ticket): Long
}
