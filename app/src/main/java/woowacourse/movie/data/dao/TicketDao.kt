package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.TicketEntity

@Dao
interface TicketDao {
    @Insert
    fun saveTicket(ticket: TicketEntity)

    @Query("SELECT * from tickets")
    fun getAllTickets(): List<TicketEntity>
}
