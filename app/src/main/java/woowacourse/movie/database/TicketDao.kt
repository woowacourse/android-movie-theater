package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.ticket.model.DbTicket

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticketDb")
    fun getAll(): List<DbTicket>

    @Query("DELETE FROM ticketDb")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tickets: DbTicket)

    @Delete
    fun delete(tickets: DbTicket)
}
