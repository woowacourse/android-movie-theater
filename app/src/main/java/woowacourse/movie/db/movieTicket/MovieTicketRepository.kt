package woowacourse.movie.db.movieTicket

import android.database.Cursor
import woowacourse.movie.model.MovieTicketModel

interface MovieTicketRepository {
    fun insertMovieTicket(movieTicket: MovieTicketModel): Int
    fun getTicketId(): Cursor
}
