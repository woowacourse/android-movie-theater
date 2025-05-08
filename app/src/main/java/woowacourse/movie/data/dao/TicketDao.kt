package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.TicketEntity

@Dao
interface TicketDao {
    @Query("SELECT * FROM ticket")
    fun getAll(): List<TicketEntity>

    @Insert
    fun insertAll(vararg tickets: TicketEntity)
}
