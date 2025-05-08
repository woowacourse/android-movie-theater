package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieTicketDao {
    @Insert
    fun insertTicket(ticket: MovieTicket)

    @Delete
    fun deleteTicket(ticket: MovieTicket)

    @Query("SELECT * FROM movieTicket WHERE uid = :uid")
    fun getTicketByUid(uid: Int): MovieTicket

    @Query("SELECT * FROM movieticket")
    fun getAll(): List<MovieTicket>
}
