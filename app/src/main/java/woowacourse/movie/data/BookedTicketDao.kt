package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface BookedTicketDao {
     @Insert
    fun insert(bookedTicketEntity: BookedTicketEntity): Long
}