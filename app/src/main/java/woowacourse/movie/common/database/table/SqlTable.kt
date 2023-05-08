package woowacourse.movie.common.database.table

import woowacourse.movie.common.database.SqlColumn

interface SqlTable {
    val name: String
    val scheme: List<SqlColumn>
}
