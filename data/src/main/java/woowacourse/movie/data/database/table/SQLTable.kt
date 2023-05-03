package woowacourse.movie.data.database.table

import woowacourse.movie.data.database.SQLColumn

interface SQLTable {
    val name: String
    val scheme: List<SQLColumn>
}
