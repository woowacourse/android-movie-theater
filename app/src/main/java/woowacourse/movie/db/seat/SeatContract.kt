package woowacourse.movie.db.seat

object SeatContract {
    const val TABLE_NAME = "seats"
    const val TABLE_COLUMN_SEAT_ID = "seatId"
    const val TABLE_COLUMN_ROW = "row"
    const val TABLE_COLUMN_COL = "col"
    const val TABLE_COLUMN_RANK = "rank"

    internal const val CREATE_TABLE =
        "CREATE TABLE $TABLE_NAME (" +
            "$TABLE_COLUMN_SEAT_ID integer PRIMARY KEY AUTOINCREMENT," +
            "$TABLE_COLUMN_ROW integer," +
            "$TABLE_COLUMN_COL integer," +
            "$TABLE_COLUMN_RANK text);"

    internal const val DROP_TABLE =
        "DROP TABLE if exists $TABLE_NAME;"
}
