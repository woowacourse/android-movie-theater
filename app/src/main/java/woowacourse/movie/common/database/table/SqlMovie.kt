package woowacourse.movie.common.database.table

import woowacourse.movie.common.database.SqlColumn
import woowacourse.movie.common.database.SqlType

object SqlMovie : SqlTable {
    const val ID = "ID"
    const val POSTER = "POSTER"
    const val TITLE = "TITLE"
    const val START_DATE = "START_DATE"
    const val END_DATE = "END_DATE"
    const val RUNNING_TIME = "RUNNING_TIME"
    const val DESCRIPTION = "DESCRIPTION"

    override val name: String = "MOVIE"
    override val scheme: List<SqlColumn> = listOf(
        SqlColumn(ID, SqlType.INTEGER, "PRIMARY KEY AUTOINCREMENT"),
        SqlColumn(POSTER, SqlType.INTEGER),
        SqlColumn(TITLE, SqlType.TEXT),
        SqlColumn(START_DATE, SqlType.TEXT),
        SqlColumn(END_DATE, SqlType.TEXT),
        SqlColumn(RUNNING_TIME, SqlType.INTEGER),
        SqlColumn(DESCRIPTION, SqlType.TEXT),
    )
}
