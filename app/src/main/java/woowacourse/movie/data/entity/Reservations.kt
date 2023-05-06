package woowacourse.movie.data.entity

import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.data.db.SeatContract
import woowacourse.movie.data.db.TicketContract
import woowacourse.movie.data.db.TicketSeatContract
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.model.seat.ColumnModel
import woowacourse.movie.ui.model.seat.RankModel
import woowacourse.movie.ui.model.seat.RowModel
import woowacourse.movie.ui.model.seat.SeatModel
import java.time.LocalDateTime

object Reservations {
    private val items = mutableListOf<MovieTicketModel>()

    fun restore(db: SQLiteDatabase) {
        val cursor = TicketContract.readRecords(db)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(TicketContract.COLUMN_ID))
            val seats = getSeats(db, id)
            val ticket = MovieTicketModel(
                title = cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.COLUMN_TITLE)),
                time = TicketTimeModel(LocalDateTime.parse(cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.COLUMN_DATE_TIME)))),
                peopleCount = PeopleCountModel(cursor.getInt(cursor.getColumnIndexOrThrow(TicketContract.COLUMN_PEOPLE_COUNT))),
                seats = seats,
                price = PriceModel(cursor.getInt(cursor.getColumnIndexOrThrow(TicketContract.COLUMN_PRICE))),
                theaterName = cursor.getString(cursor.getColumnIndexOrThrow(TicketContract.COLUMN_THEATER_NAME)),
            )
            addItem(ticket)
        }
    }

    private fun getSeats(db: SQLiteDatabase, ticketId: Int): Set<SeatModel> {
        val seats = mutableSetOf<SeatModel>()
        val cursor = TicketSeatContract.readRecords(db, ticketId)
        while (cursor.moveToNext()) {
            val seatId =
                cursor.getInt(cursor.getColumnIndexOrThrow(TicketSeatContract.COLUMN_SEAT_ID))
            val seat = getSeat(db, seatId)
            seats.add(seat)
        }
        return seats
    }

    private fun getSeat(db: SQLiteDatabase, seatId: Int): SeatModel {
        val cursor = SeatContract.readRecordById(db, seatId)
        cursor.moveToFirst()
        return SeatModel(
            row = RowModel.of(cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.COLUMN_ROW))),
            column = ColumnModel.of(cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.COLUMN_COLUMN))),
            rank = RankModel.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(SeatContract.COLUMN_RANK))),
        )
    }

    fun addItem(item: MovieTicketModel) {
        items.add(item)
    }

    fun getAll(): List<MovieTicketModel> = items

    fun getSize(): Int = items.size
}
