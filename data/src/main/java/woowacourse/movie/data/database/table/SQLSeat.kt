package woowacourse.movie.data.database.table

import woowacourse.movie.data.database.SQLColumn
import woowacourse.movie.data.database.SQLType

object SQLSeat : SQLTable {
    const val RESERVATION_ID = "RESERVATION_ID"
    const val ROW = "ROW"
    const val COLUMN = "COLUMN"

    override val name: String = "SEAT"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(RESERVATION_ID, SQLType.INTEGER),
        SQLColumn(ROW, SQLType.INTEGER),
        SQLColumn(COLUMN, SQLType.INTEGER)
    )
}
