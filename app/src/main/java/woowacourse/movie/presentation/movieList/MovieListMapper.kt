package woowacourse.movie.presentation.movieList

import woowacourse.movie.model.theater.Theater
import woowacourse.movie.presentation.movieList.model.AdItemDisplay
import woowacourse.movie.presentation.movieList.model.MovieDisplay
import woowacourse.movie.presentation.movieList.model.MovieItemDisplay

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
