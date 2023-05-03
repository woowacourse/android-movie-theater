package woowacourse.movie.data.database.table

import woowacourse.movie.data.database.SQLColumn
import woowacourse.movie.data.database.SQLType

object SQLMovie : SQLTable {
    const val ID = "ID"
    const val POSTER = "POSTER"
    const val TITLE = "TITLE"
    const val START_DATE = "START_DATE"
    const val END_DATE = "END_DATE"
    const val RUNNING_TIME = "RUNNING_TIME"
    const val DESCRIPTION = "DESCRIPTION"

    override val name: String = "MOVIE"
    override val scheme: List<SQLColumn> = listOf(
        SQLColumn(ID, SQLType.INTEGER, "PRIMARY KEY AUTOINCREMENT"),
        SQLColumn(POSTER, SQLType.INTEGER),
        SQLColumn(TITLE, SQLType.TEXT),
        SQLColumn(START_DATE, SQLType.TEXT),
        SQLColumn(END_DATE, SQLType.TEXT),
        SQLColumn(RUNNING_TIME, SQLType.INTEGER),
        SQLColumn(DESCRIPTION, SQLType.TEXT),
    )
}
