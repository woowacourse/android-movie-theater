package woowacourse.movie.data.sqlite

object ReservationTicketsContract : TicketsDataBaseTableContract() {
    override val TABLE_NAME = "reservation_tickets_table"
    const val TABLE_COLUMN_RESERVATION_ID = "reservation_id"
    const val TABLE_COLUMN_THEATER_ID = "theater_id"
    const val TABLE_COLUMN_MOVIE_ID = "movie_id"
    const val TABLE_COLUMN_YEAR = "year"
    const val TABLE_COLUMN_MONTH = "month"
    const val TABLE_COLUMN_DAY = "day"
    const val TABLE_COLUMN_HOUR = "hour"
    const val TABLE_COLUMN_MINUTES = "minutes"
    const val TABLE_COLUMN_ALL_DISCOUNTED_MONEY = "all_discounted_money"

    override fun allColumn() = listOf(
        TABLE_COLUMN_RESERVATION_ID,
        TABLE_COLUMN_THEATER_ID,
        TABLE_COLUMN_MOVIE_ID,
        TABLE_COLUMN_YEAR,
        TABLE_COLUMN_MONTH,
        TABLE_COLUMN_DAY,
        TABLE_COLUMN_HOUR,
        TABLE_COLUMN_MINUTES,
        TABLE_COLUMN_ALL_DISCOUNTED_MONEY
    )

    override fun createSQL(): String {
        return "CREATE TABLE $TABLE_NAME(" +
            "  $TABLE_COLUMN_RESERVATION_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "  $TABLE_COLUMN_THEATER_ID int," +
            "  $TABLE_COLUMN_MOVIE_ID int," +
            "  $TABLE_COLUMN_YEAR int," +
            "  $TABLE_COLUMN_MONTH int," +
            "  $TABLE_COLUMN_DAY int," +
            "  $TABLE_COLUMN_HOUR int," +
            "  $TABLE_COLUMN_MINUTES int," +
            "  $TABLE_COLUMN_ALL_DISCOUNTED_MONEY int" +
            ");"
    }
}
