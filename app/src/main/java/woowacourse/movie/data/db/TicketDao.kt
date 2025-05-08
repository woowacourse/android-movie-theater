package woowacourse.movie.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert
    fun insert(entity: TicketEntity): Long

    @Query("SELECT * FROM table_booking")
    fun readAll(): List<TicketEntity>

    @Query("SELECT * FROM table_booking WHERE id = :ticketId")
    fun readById(ticketId: Long): TicketEntity
}
