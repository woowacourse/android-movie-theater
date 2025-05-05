package woowacourse.movie.domain.model

import java.io.Serializable

data class Theater(
    val name: String,
    val theaterSchedules: TheaterSchedules,
) : Serializable {
    fun scheduleCountByMovieId(movieId: Long) = theaterSchedules[movieId].size
}
