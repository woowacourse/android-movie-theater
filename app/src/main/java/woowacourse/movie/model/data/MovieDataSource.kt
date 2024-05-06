package woowacourse.movie.model.data

interface MovieDataSource<T> {
    fun save(data: T): Long

    fun find(id: Long): T

    fun findAll(): List<T>

    fun deleteAll()
}
