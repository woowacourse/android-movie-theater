package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.domain.model.movieticket.MovieTicket

@Dao
interface MovieTicketDao {

    @Insert
    fun insertMovieTicket(movieTicket: MovieTicket): Long

    @Query("SELECT * FROM movie_ticket WHERE id = :movieTicketId")
    fun getMovieTicket(movieTicketId: Long): MovieTicket?
}
