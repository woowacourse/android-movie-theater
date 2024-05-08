package woowacourse.movie.model.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import woowacourse.movie.model.data.DefaultMovieDataSource

@Dao
interface TicketDao {
    @Upsert
    fun save(data: TicketEntity): Long

    @Query("SELECT * FROM tickets WHERE id=:id")
    fun find(id: Long): TicketEntity

    @Query("SELECT * FROM tickets")
    fun findAll(): List<TicketEntity>

    @Query("DELETE FROM tickets")
    fun deleteAll()
}
