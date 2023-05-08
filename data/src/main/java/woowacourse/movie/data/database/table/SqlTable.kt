package woowacourse.movie.data.database.table

import woowacourse.movie.data.database.SqlColumn

interface SqlTable {
    val name: String
    val scheme: List<SqlColumn>
}
