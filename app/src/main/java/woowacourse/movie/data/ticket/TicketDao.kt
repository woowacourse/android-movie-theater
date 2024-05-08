package woowacourse.movie.data.ticket

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.ticket.entity.Ticket

@Dao
interface TicketDao {
    @Query("SELECT * FROM tickets")
    fun getAll(): List<Ticket>

    @Query("SELECT * FROM tickets WHERE id = :id")
    @Throws(SQLiteException::class)
    fun find(id: Long): Ticket

    @Insert
    fun insert(ticket: Ticket): Long

    @Query("DELETE FROM tickets")
    fun deleteAll()
}
