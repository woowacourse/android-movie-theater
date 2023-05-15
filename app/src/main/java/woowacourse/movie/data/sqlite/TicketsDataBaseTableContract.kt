package woowacourse.movie.data.sqlite

sealed class TicketsDataBaseTableContract {
    abstract val TABLE_NAME: String
    abstract fun allColumn(): List<String>
    abstract fun createSQL(): String
}
