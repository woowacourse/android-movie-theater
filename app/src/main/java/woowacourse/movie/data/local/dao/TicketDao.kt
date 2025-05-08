package woowacourse.movie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.local.entity.TicketEntity

@Dao
interface TicketDao {
    @Insert
    fun insert(ticketEntity: TicketEntity)

    @Query("SELECT * FROM ticket")
    fun getAll(): List<TicketEntity>
}
