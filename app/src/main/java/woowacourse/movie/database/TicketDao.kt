package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert
    fun insertTicket(ticket: Ticket)

    @Query("SELECT * FROM ticket")
    fun getAllTickets(): List<Ticket>
}
