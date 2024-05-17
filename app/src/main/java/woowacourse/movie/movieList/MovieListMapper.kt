package woowacourse.movie.movieList

import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ui.Ad
import woowacourse.movie.model.ui.Movie
import woowacourse.movie.model.ui.MovieItem

fun List<Theater>.toMovieDisplays(): List<MovieItem> {
    return map(Theater::toMovieItemDisplay)
        .chunked(3)
        .flatMap { it + Ad() }
}

fun Theater.toMovieItemDisplay(): Movie {
    return Movie(
        title = movie.title.name,
        releaseDate = movie.releaseDate.date,
        runningTime = movie.runningTime.time,
    )
}
