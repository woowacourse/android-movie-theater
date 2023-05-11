package woowacourse.movie.db.movieTicket

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.utils.reservationFormat

class MovieTicketData(
    private val db: SQLiteDatabase
) : MovieTicketRepository {
    override fun insertMovieTicket(movieTicket: MovieTicketModel): Int {
        val value = ContentValues()
        value.put(MovieTicketContract.TABLE_COLUMN_THEATER, movieTicket.theater)
        value.put(MovieTicketContract.TABLE_COLUMN_TITLE, movieTicket.title)
        value.put(
            MovieTicketContract.TABLE_COLUMN_TIME,
            movieTicket.time.dateTime.reservationFormat()
        )
        value.put(MovieTicketContract.TABLE_COLUMN_COUNT, movieTicket.peopleCount.count)
        value.put(MovieTicketContract.TABLE_COLUMN_PRICE, movieTicket.price.amount)

        return db.insert(MovieTicketContract.TABLE_NAME, null, value).toInt()
    }

    override fun getTicketId(): Cursor =
        db.query(
            MovieTicketContract.TABLE_NAME,
            null,
            null,
            null,
            MovieTicketContract.TABLE_COLUMN_TICKET_ID,
            null,
            null
        )
}
