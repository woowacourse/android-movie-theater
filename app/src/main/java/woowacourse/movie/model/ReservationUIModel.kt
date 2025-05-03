package woowacourse.movie.model

import woowacourse.movie.view.item.Movie

data class ReservationUIModel(
    val movie: Movie,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticketCount: Int,
    val theaterName: String,
)
