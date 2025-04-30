package woowacourse.movie.domain.model.theater

class Theaters(
    private val value: List<Theater>,
) {
    operator fun get(index: Int) = value[index]

    fun size() = value.size
}
