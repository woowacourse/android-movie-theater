package woowacourse.movie.movieList

import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ui.AdItemDisplay
import woowacourse.movie.model.ui.MovieDisplay
import woowacourse.movie.model.ui.MovieItemDisplay

fun List<Theater>.toMovieDisplays(): List<MovieDisplay> {
    return map(Theater::toMovieItemDisplay)
        .chunked(3)
        .flatMap { it + AdItemDisplay() }
}

fun Theater.toMovieItemDisplay(): MovieItemDisplay {
    return MovieItemDisplay(
        title = movie.title.name,
        releaseDate = movie.releaseDate.date,
        runningTime = movie.runningTime.time,
    )
}
