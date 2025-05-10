package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import woowacourse.movie.data.entity.BookedTicketEntity

@Dao
interface BookedTicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookedTicket(bookedTicketEntity: BookedTicketEntity)

    @Query("SELECT * FROM booked_tickets")
    fun getAll(): List<BookedTicketEntity>
}
