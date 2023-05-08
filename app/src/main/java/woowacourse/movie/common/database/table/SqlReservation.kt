package woowacourse.movie.common.database.table

import woowacourse.movie.common.database.SqlColumn
import woowacourse.movie.common.database.SqlType

object SqlReservation : SqlTable {
    const val ID = "ID"
    const val DATE = "DATE"
    const val PEOPLE_COUNT = "PEOPLE_COUNT"
    const val MOVIE_ID = "MOVIE_ID"
    const val PRICE = "PRICE"
    const val THEATER_NAME = "THEATER_NAME"

    override val name: String = "RESERVATION"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(ID, SqlType.INTEGER, "PRIMARY KEY AUTOINCREMENT"),
        SqlColumn(DATE, SqlType.TEXT),
        SqlColumn(PEOPLE_COUNT, SqlType.INTEGER),
        SqlColumn(MOVIE_ID, SqlType.INTEGER),
        SqlColumn(PRICE, SqlType.INTEGER),
        SqlColumn(THEATER_NAME, SqlType.TEXT),
    )
}
