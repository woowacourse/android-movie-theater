package woowacourse.movie.data.ticket

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.ticket.TicketEntity.Companion.TICKET_TABLE_NAME

@Dao
interface TicketDao {
    @Insert
    fun insert(ticket: TicketEntity)

    @Query("SELECT * FROM $TICKET_TABLE_NAME")
    fun getAll(): List<TicketEntity>

    @Query("DELETE FROM $TICKET_TABLE_NAME")
    fun deleteAll()
}
