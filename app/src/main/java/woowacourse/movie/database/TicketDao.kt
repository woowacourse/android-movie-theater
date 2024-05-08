package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface TicketDao {
    @Insert
    fun insertTicket(ticket: Ticket)
}
