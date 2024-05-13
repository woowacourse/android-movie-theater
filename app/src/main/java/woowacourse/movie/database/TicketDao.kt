package woowacourse.movie.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.ticket.model.TicketEntity

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket")
    fun getAll(): List<TicketEntity>

    @Query("DELETE FROM ticket")
    fun deleteAll()

    @Query("SELECT * FROM ticket ORDER BY id DESC LIMIT 1")
    fun getLast(): TicketEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tickets: TicketEntity)

    @Delete
    fun delete(tickets: TicketEntity)
}
