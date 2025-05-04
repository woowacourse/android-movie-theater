package woowacourse.movie.domain.model.theater

import java.io.Serializable

class Theaters(
    private val value: List<Theater>,
) : Serializable {
    operator fun get(index: Int) = value[index]

    fun availableTheaters(movieId: Int) = Theaters(value.filter { it.screeningTimeCount(movieId) > 0 })

    fun size() = value.size
}
