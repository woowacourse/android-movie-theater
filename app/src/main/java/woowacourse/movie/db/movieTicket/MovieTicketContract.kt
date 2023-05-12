package woowacourse.movie.db.movieTicket

object MovieTicketContract {
    const val TABLE_NAME = "ticket"
    const val TABLE_COLUMN_TICKET_ID = "ticketId"
    const val TABLE_COLUMN_THEATER = "theater"
    const val TABLE_COLUMN_TITLE = "title"
    const val TABLE_COLUMN_TIME = "time"
    const val TABLE_COLUMN_COUNT = "count"
    const val TABLE_COLUMN_PRICE = "price"

    internal const val CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
            "$TABLE_COLUMN_TICKET_ID integer PRIMARY KEY AUTOINCREMENT," +
            "$TABLE_COLUMN_THEATER text," +
            "$TABLE_COLUMN_TITLE text," +
            "$TABLE_COLUMN_TIME text," +
            "$TABLE_COLUMN_COUNT integer," +
            "$TABLE_COLUMN_PRICE integer);"

    internal const val DROP_TABLE = "DROP TABLE if exists $TABLE_NAME;"
}
