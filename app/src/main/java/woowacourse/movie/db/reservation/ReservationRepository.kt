package woowacourse.movie.db.reservation

import android.database.Cursor

interface ReservationRepository {
    fun insertReservationIds(ticketId: Int, seatId: Int)
    fun getReservationIds(ticketId: Int): Cursor
}
