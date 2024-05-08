package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert
    suspend fun insertTicket(ticket: Ticket)

    @Query("SELECT * FROM Ticket")
    suspend fun getAllPurchases(): List<Ticket>
}
