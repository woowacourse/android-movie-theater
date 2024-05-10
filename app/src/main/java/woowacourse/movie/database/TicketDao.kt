package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: Ticket): Long

    @Query("SELECT * FROM ticket")
    fun getAllTickets(): List<Ticket>

    @Query("SELECT * FROM ticket WHERE id = :ticketId")
    fun getTicketById(ticketId: Int): Ticket?
}
