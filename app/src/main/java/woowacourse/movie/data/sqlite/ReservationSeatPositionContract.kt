package woowacourse.movie.data.sqlite

object ReservationSeatPositionContract : TicketsDataBaseTableContract() {
    override val TABLE_NAME = "reservation_seat_position_table"
    const val TABLE_COLUMN_RESERVATION_ID = "reservation_id"
    const val TABLE_COLUMN_SEAT_POSITION_ROW = "row"
    const val TABLE_COLUMN_SEAT_POSITION_COLUMN = "column"
    const val TABLE_COLUMN_DISCOUNTED_MONEY = "discounted_money"

    override fun allColumn() = listOf(
        TABLE_COLUMN_RESERVATION_ID,
        TABLE_COLUMN_SEAT_POSITION_ROW,
        TABLE_COLUMN_SEAT_POSITION_COLUMN,
        TABLE_COLUMN_DISCOUNTED_MONEY
    )

    override fun createSQL(): String {
        return "CREATE TABLE $TABLE_NAME(" +
            "  $TABLE_COLUMN_RESERVATION_ID int," +
            "  $TABLE_COLUMN_SEAT_POSITION_ROW int," +
            "  $TABLE_COLUMN_SEAT_POSITION_COLUMN int," +
            "  $TABLE_COLUMN_DISCOUNTED_MONEY int" +
            ");"
    }
}
