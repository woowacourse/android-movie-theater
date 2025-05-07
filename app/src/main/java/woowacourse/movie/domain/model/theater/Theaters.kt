package woowacourse.movie.domain.model.theater

import java.time.LocalDateTime

class Theaters(
    private val value: List<Theater>,
) {
    operator fun get(index: Int) = value[index]

    fun bookingAbleTheater(movieId: Int) = value.filter { it.screeningTimeCount(movieId) > 0 }

    fun selectedMovieScreeningTimes(
        movieId: Int,
        theaterName: String,
    ): List<LocalDateTime> {
        return value
            .find { it.name == theaterName }
            ?.getMovieScreening(movieId)
            ?: emptyList()
    }
}
