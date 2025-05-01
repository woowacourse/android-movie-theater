package woowacourse.movie.domain.model.theater

class Theaters(
    private val value: List<Theater>,
) {
    operator fun get(index: Int) = value[index]

    fun bookingAbleTheater(movieId: Int) = Theaters(value.filter { it.screeningTimeCount(movieId) > 0 })

    fun size() = value.size
}
