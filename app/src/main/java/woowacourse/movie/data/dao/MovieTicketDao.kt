package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.model.MovieTicketEntity

@Dao
interface MovieTicketDao {
    @Insert
    fun insertMovieTicket(movieTicketEntity: MovieTicketEntity): Long

    @Query("SELECT * FROM movie_ticket WHERE id = :movieTicketId")
    fun getMovieTicket(movieTicketId: Long): MovieTicketEntity?

    @Query("SELECT * FROM movie_ticket")
    fun getAllMovieTickets(): List<MovieTicketEntity>
}
