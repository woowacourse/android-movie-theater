package woowacourse.movie.data.database.table

import woowacourse.movie.data.database.SqlColumn
import woowacourse.movie.data.database.SqlType

object SqlSeat : SqlTable {
    const val RESERVATION_ID = "RESERVATION_ID"
    const val ROW = "ROW"
    const val COLUMN = "COLUMN"

    override val name: String = "SEAT"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(RESERVATION_ID, SqlType.INTEGER),
        SqlColumn(ROW, SqlType.INTEGER),
        SqlColumn(COLUMN, SqlType.INTEGER)
    )
}
