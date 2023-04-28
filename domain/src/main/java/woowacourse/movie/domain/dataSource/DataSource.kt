package woowacourse.movie.domain.dataSource

interface DataSource<T> {
    val value: List<T>
    fun add(t: T)
}
