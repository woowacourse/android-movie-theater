package woowacourse.movie.domain.model.theater

import java.io.Serializable

class Theaters(
    private val items: List<Theater>,
) : Serializable {
    operator fun get(index: Int) = items[index]

    fun availableTheaters(movieId: Int) = Theaters(items.filter { theater -> theater.screeningCount(movieId) > 0 })

    fun size() = items.size
}
