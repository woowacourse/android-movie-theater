package woowacourse.movie.data.database.table

import woowacourse.movie.data.database.SQLColumn
import woowacourse.movie.data.database.SQLType

object SQLReservation : SQLTable {
    const val ID = "ID"
    const val DATE = "DATE"
    const val PEOPLE_COUNT = "PEOPLE_COUNT"
    const val MOVIE_ID = "MOVIE_ID"
    const val PRICE = "PRICE"

    override val name: String = "RESERVATION"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(ID, SQLType.INTEGER, "PRIMARY KEY AUTOINCREMENT"),
        SQLColumn(DATE, SQLType.TEXT),
        SQLColumn(PEOPLE_COUNT, SQLType.INTEGER),
        SQLColumn(MOVIE_ID, SQLType.INTEGER),
        SQLColumn(PRICE, SQLType.INTEGER),
    )
}
