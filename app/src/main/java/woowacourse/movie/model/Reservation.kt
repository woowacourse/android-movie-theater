package woowacourse.movie.model

import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat

data class Reservation(
    val id: Long = NONE_ID,
    val cinemaName: String,
    val title: Title,
    val releaseDate: MovieDate,
    val runningTime: RunningTime,
    val synopsis: Synopsis,
    val seats: Set<Seat>,
) {
    val totalPrice: Int = seats.sumOf { it.price }

    companion object {
        const val NONE_ID = -1L
    }
}