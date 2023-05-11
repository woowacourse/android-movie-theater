package woowacourse.movie.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.movie.db.movieTicket.MovieTicketContract
import woowacourse.movie.db.reservation.ReservationContract
import woowacourse.movie.db.seat.SeatContract
import woowacourse.movie.db.seat.SeatData
import woowacourse.movie.model.seat.SeatsModel

class ReservationDBHelper(context: Context) : SQLiteOpenHelper(context, "reservation.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MovieTicketContract.CREATE_TABLE)
        db.execSQL(SeatContract.CREATE_TABLE)
        insertSeats(db)
        db.execSQL(ReservationContract.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(MovieTicketContract.DROP_TABLE)
        db.execSQL(SeatContract.DROP_TABLE)
        db.execSQL(ReservationContract.DROP_TABLE)
        onCreate(db)
    }

    private fun insertSeats(db: SQLiteDatabase) {
        val seatData = SeatData(db)
        SeatsModel().getAll().forEach { seatData.insertSeat(it) }
    }
}
