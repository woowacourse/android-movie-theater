package woowacourse.movie.data.repository.remote

import woowacourse.movie.data.repository.remote.DummyData.theaters
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.domain.repository.TheaterRepository

object DummyTheater : TheaterRepository {
    override fun findTheaterNameById(theaterId: Int): Result<String> =
        runCatching {
            theaters.find { it.id == theaterId }?.name ?: throw NoSuchElementException()
        }

    override fun findTheaterCount(id: Int): Result<List<TheaterCount>> =
        runCatching {
            val tmpList: MutableList<TheaterCount> = mutableListOf()
            theaters.forEach { theater ->
                val size = theater.findScreenTimeCount(id)
                if (size != 0) {
                    tmpList.add(
                        TheaterCount(
                            id = theater.id,
                            name = theater.name,
                            size = size,
                        ),
                    )
                }
            }
            tmpList
        }
}
