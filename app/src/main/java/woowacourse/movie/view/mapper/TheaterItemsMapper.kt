package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.view.home.movies.model.TheaterRvItem

fun Theater.toItem(
    movieId: Int,
    formatter: String,
): TheaterRvItem.TheaterItem {
    val count = screeningTimeCount(movieId)
    return TheaterRvItem.TheaterItem(
        name = name,
        movieId = movieId,
        bookingAbleTimeCount = formatter.format(count),
    )
}
