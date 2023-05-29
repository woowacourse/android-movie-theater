package woowacourse.movie.data

interface DataSource<T> {
    fun getData(): T
    fun saveData(dataSource: T)
}
