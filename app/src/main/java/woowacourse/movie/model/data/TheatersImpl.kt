package woowacourse.movie.model.data

import woowacourse.movie.model.movie.Theater

object TheatersImpl : Theaters {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val theaters = mutableMapOf<Long, Theater>()

    override fun save(theater: Theater): Long {
        theaters[id] = theater.copy(id = id)
        return id++
    }

    override fun find(id: Long): Theater {
        return theaters[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Theater> {
        return theaters.map { it.value }
    }

    override fun deleteAll() {
        theaters.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
