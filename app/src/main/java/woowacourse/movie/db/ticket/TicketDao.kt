package woowacourse.movie.db.ticket

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ticket: Ticket): Long

    @Query("SELECT * FROM reservation_history WHERE uid LIKE :uid")
    fun find(uid: Long): Ticket

    @Query("SELECT * FROM reservation_history")
    fun findAll(): List<Ticket>
}
