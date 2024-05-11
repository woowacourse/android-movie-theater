package woowacourse.movie.model.ui.repository

import woowacourse.movie.data.database.theater.TheaterDao
import woowacourse.movie.data.database.theater.TheaterEntity

object FakeTheaterRepository : TheaterDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val theaters = mutableMapOf<Long, TheaterEntity>()

    init {
        save(
            TheaterEntity(
                name = "강남",
                screeningTimes = listOf("11:00", "13:00", "15:00"),
            ),
        )

        save(
            TheaterEntity(
                name = "선릉",
                screeningTimes = listOf("14:00", "16:00"),
            ),
        )
    }

    override fun save(data: TheaterEntity): Long {
        theaters[id] = data.copy(id = id)
        return id++
    }

    override fun find(id: Long): TheaterEntity {
        return theaters[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<TheaterEntity> {
        return theaters.map { it.value }
    }

    override fun deleteAll() {
        theaters.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
