package woowacourse.movie.db.reservation

import woowacourse.movie.db.movieTicket.MovieTicketContract
import woowacourse.movie.db.seat.SeatContract

object ReservationContract {
    const val TABLE_NAME = "reservation"
    const val TABLE_COLUMN_TICKET_ID = "ticketId"
    const val TABLE_COLUMN_SEAT_ID = "seatId"

    internal const val CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
            "$TABLE_COLUMN_TICKET_ID integer," +
            "$TABLE_COLUMN_SEAT_ID integer," +
            "PRIMARY KEY ($TABLE_COLUMN_TICKET_ID, $TABLE_COLUMN_SEAT_ID)," +
            "FOREIGN KEY ($TABLE_COLUMN_TICKET_ID)" +
            "REFERENCES ${MovieTicketContract.TABLE_NAME} (${MovieTicketContract.TABLE_COLUMN_TICKET_ID})," +
            "FOREIGN KEY ($TABLE_COLUMN_SEAT_ID)" +
            "REFERENCES ${SeatContract.TABLE_NAME} (${SeatContract.TABLE_COLUMN_SEAT_ID}));"

    internal const val DROP_TABLE =
        "DROP TABLE if exists $TABLE_NAME;"
}
