package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BookedTicketDao {
    @Insert
    fun insert(bookedTicketEntity: BookedTicketEntity): Long

    @Query("SELECT * FROM booked_tickets WHERE id = :id")
    fun findBookedTicketEntityById(id: Long): BookedTicketEntity

    @Query("SELECT * FROM booked_tickets ORDER BY screening_date_time")
    fun findAll(): List<BookedTicketEntity>
}
