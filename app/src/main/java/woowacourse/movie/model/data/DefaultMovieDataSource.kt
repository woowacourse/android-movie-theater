package woowacourse.movie.model.data

interface DefaultMovieDataSource<K, T> {
    fun save(data: T): K

    fun find(id: K): T

    fun findAll(): List<T>

    fun deleteAll()
}
