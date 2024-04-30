package woowacourse.movie.model.data

import woowacourse.movie.model.movie.Theater
import java.time.LocalTime

object TheatersImpl : Theaters {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val theaters = mutableMapOf<Long, Theater>()

    init {
        save(
            Theater(
                "강남",
                listOf(LocalTime.of(11, 0), LocalTime.of(13, 0), LocalTime.of(15, 0)),
            )
        )

        save(
            Theater(
                "선릉",
                listOf(LocalTime.of(14, 0), LocalTime.of(16, 0), LocalTime.of(18, 0)),
            )
        )

        save(
            Theater(
                "잠실",
                listOf(LocalTime.of(9, 0), LocalTime.of(11, 0)),
            )
        )
    }

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
