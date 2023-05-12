package woowacourse.movie.db.seat

import android.database.Cursor
import woowacourse.movie.model.seat.SeatModel

interface SeatRepository {
    fun insertSeat(seat: SeatModel): Int
    fun getSeatById(seatId: Int): Cursor
    fun getIdByPosition(seat: SeatModel): Cursor
}
