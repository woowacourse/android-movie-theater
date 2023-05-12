package woowacourse.movie.db

import android.database.sqlite.SQLiteDatabase
import woowacourse.movie.db.movieTicket.MovieTicketContract
import woowacourse.movie.db.movieTicket.MovieTicketData
import woowacourse.movie.db.reservation.ReservationContract
import woowacourse.movie.db.reservation.ReservationData
import woowacourse.movie.db.seat.SeatContract
import woowacourse.movie.db.seat.SeatData
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.model.seat.ColumnModel
import woowacourse.movie.model.seat.RankModel
import woowacourse.movie.model.seat.RowModel
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.utils.getReservationLocalDateTime

class ReservationDataImpl(
    db: SQLiteDatabase
) {
    private val ticketData = MovieTicketData(db)
    private val seatData = SeatData(db)
    private val reservationData = ReservationData(db)

    fun insertReservation(movieTicket: MovieTicketModel) {
        val ticketId = ticketData.insertMovieTicket(movieTicket)

        movieTicket.seats.forEach {
            val cursor = seatData.getIdByPosition(it)
            cursor.moveToFirst()
            val seatId = cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.TABLE_COLUMN_SEAT_ID))
            reservationData.insertReservationIds(ticketId, seatId)
            cursor.close()
        }
    }

    fun getReservations(): List<MovieTicketModel> {
        val reservations = mutableListOf<MovieTicketModel>()
        val cursor = ticketData.getTicketId()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(MovieTicketContract.TABLE_COLUMN_TICKET_ID))
            val seats = getSeats(id)
            val ticket = MovieTicketModel(
                theater = cursor.getString(cursor.getColumnIndexOrThrow(MovieTicketContract.TABLE_COLUMN_THEATER)),
                title = cursor.getString(cursor.getColumnIndexOrThrow(MovieTicketContract.TABLE_COLUMN_TITLE)),
                time = TicketTimeModel(cursor.getString(cursor.getColumnIndexOrThrow(MovieTicketContract.TABLE_COLUMN_TIME)).getReservationLocalDateTime()),
                peopleCount = PeopleCountModel(cursor.getInt(cursor.getColumnIndexOrThrow(MovieTicketContract.TABLE_COLUMN_COUNT))),
                seats = seats,
                price = PriceModel(cursor.getInt(cursor.getColumnIndexOrThrow(MovieTicketContract.TABLE_COLUMN_PRICE)))
            )
            reservations.add(ticket)
        }
        cursor.close()
        return reservations
    }

    private fun getSeats(ticketId: Int): Set<SeatModel> {
        val seats = mutableSetOf<SeatModel>()
        val cursor = reservationData.getReservationIds(ticketId)
        while (cursor.moveToNext()) {
            val seatId =
                cursor.getInt(cursor.getColumnIndexOrThrow(ReservationContract.TABLE_COLUMN_SEAT_ID))
            val seat = getSeat(seatId)
            seats.add(seat)
        }
        cursor.close()
        return seats
    }

    private fun getSeat(seatId: Int): SeatModel {
        val cursor = seatData.getSeatById(seatId)
        cursor.moveToFirst()
        val seat = SeatModel(
            row = RowModel.of(cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.TABLE_COLUMN_ROW))),
            column = ColumnModel.of(cursor.getInt(cursor.getColumnIndexOrThrow(SeatContract.TABLE_COLUMN_COL))),
            rank = RankModel.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(SeatContract.TABLE_COLUMN_RANK))),
        )
        cursor.close()
        return seat
    }
}
