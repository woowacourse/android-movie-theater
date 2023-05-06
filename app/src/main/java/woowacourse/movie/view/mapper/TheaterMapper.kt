package woowacourse.movie.view.mapper

import woowacourse.movie.domain.Theater
import woowacourse.movie.view.model.MovieTheater

fun Theater.toMovieTheater(movieId: Int): MovieTheater {
    val screening = screenings.find {
        it.movieId == movieId
    }
    return MovieTheater(
        name,
        screening?.timeslot ?: emptyList()
    )
}
