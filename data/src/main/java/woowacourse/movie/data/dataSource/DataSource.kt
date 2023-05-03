package woowacourse.movie.data.dataSource

interface DataSource<T> {
    val value: List<T>
    fun add(t: T)
}
