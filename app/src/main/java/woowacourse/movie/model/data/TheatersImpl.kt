package woowacourse.movie.model.data

import woowacourse.movie.model.movie.Theater
import java.time.LocalTime

object TheatersImpl : MovieDataSource<Theater> {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val theaters = mutableMapOf<Long, Theater>()

    init {
        save(
            Theater(
                "강남",
                listOf(LocalTime.of(11, 0), LocalTime.of(13, 0), LocalTime.of(15, 0)),
            ),
        )

        save(
            Theater(
                "선릉",
                listOf(LocalTime.of(14, 0), LocalTime.of(16, 0)),
            ),
        )

        save(
            Theater(
                "잠실",
                listOf(
                    LocalTime.of(9, 0),
                    LocalTime.of(11, 0),
                    LocalTime.of(13, 0),
                    LocalTime.of(15, 0),
                ),
            ),
        )
        save(
            Theater(
                "강남",
                listOf(LocalTime.of(11, 0)),
            ),
        )

        save(
            Theater(
                "선릉",
                listOf(LocalTime.of(20, 0), LocalTime.of(22, 0)),
            ),
        )

        save(
            Theater(
                "잠실",
                listOf(LocalTime.of(9, 0), LocalTime.of(11, 0)),
            ),
        )
        save(
            Theater(
                "강남",
                listOf(LocalTime.of(5, 0), LocalTime.of(7, 0)),
            ),
        )

        save(
            Theater(
                "선릉",
                listOf(LocalTime.of(20, 0)),
            ),
        )

        save(
            Theater(
                "잠실",
                listOf(LocalTime.of(19, 0), LocalTime.of(21, 0), LocalTime.of(23, 0)),
            ),
        )

        save(
            Theater(
                "강남",
                listOf(LocalTime.of(11, 0), LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(17, 0), LocalTime.of(19, 0)),
            ),
        )

        save(
            Theater(
                "선릉",
                listOf(LocalTime.of(2, 0), LocalTime.of(4, 0), LocalTime.of(6, 0)),
            ),
        )

        save(
            Theater(
                "잠실",
                listOf(LocalTime.of(22, 0)),
            ),
        )
    }

    override fun save(data: Theater): Long {
        theaters[id] = data.copy(id = id)
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
