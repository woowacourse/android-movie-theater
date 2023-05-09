package woowacourse.movie.common.database

data class SqlColumn(
    val name: String,
    val type: SqlType,
    val constraint: String = ""
)
