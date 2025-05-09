package woowacourse.movie.model.reservation

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.MovieDate
import woowacourse.movie.model.movie.MovieTime

data class ReservationUIModel(
    val movie: Movie,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticketCount: Int,
    val theaterName: String,
)
