package woowacourse.movie.model.data

import woowacourse.movie.model.movie.Theater

interface Theaters {
    fun find(id: Long): Theater

    fun findAll(): List<Theater>

    fun save(theater: Theater): Long

    fun deleteAll()
}
