package woowacourse.movie.data.entity

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.movie.createMovieTicketModel
import woowacourse.movie.data.db.DBHelper
import woowacourse.movie.data.db.SeatContract
import woowacourse.movie.data.db.TicketContract
import woowacourse.movie.data.db.TicketSeatContract
import woowacourse.movie.ui.model.seat.ColumnModel
import woowacourse.movie.ui.model.seat.RankModel
import woowacourse.movie.ui.model.seat.RowModel
import woowacourse.movie.ui.model.seat.SeatModel

class ReservationsTest {
    private lateinit var database: SQLiteDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val dbHelper = DBHelper(context)
        database = dbHelper.writableDatabase
        dbHelper.onUpgrade(database, 1, 1)
    }

    @Test
    fun restore() {
        // given
        val ticket = createMovieTicketModel(seats = setOf(SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.B)))
        val ticketId = TicketContract.createRecord(database, ticket)
        ticket.seats.forEach {
            val cursor = SeatContract.readRecordBySeat(database, it)
            cursor.moveToFirst()
            val seatId = cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.COLUMN_ID))
            TicketSeatContract.createRecord(database, ticketId, seatId)
        }

        // when
        Reservations.restore(database)

        // then
        val actual = Reservations.getAll()
        assertThat(actual).contains(ticket)
    }
}
