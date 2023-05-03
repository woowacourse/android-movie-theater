package woowacourse.movie.data.database

data class SQLColumn(
    val name: String,
    val type: SQLType,
    val constraint: String = ""
)
