package woowacourse.movie.model.data

import woowacourse.movie.model.movie.Reservation

object ReservationsImpl : MovieDataSource<Reservation> {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val tickets = mutableMapOf<Long, Reservation>()

    override fun save(data: Reservation): Long {
        tickets[id] = data.copy(id = id)
        return id++
    }

    override fun find(id: Long): Reservation {
        return tickets[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Reservation> {
        return tickets.map { it.value }
    }

    override fun deleteAll() {
        tickets.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
