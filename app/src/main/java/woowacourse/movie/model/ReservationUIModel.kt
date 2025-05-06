package woowacourse.movie.model

data class ReservationUIModel(
    val movie: Movie,
    val movieDate: MovieDate,
    val movieTime: MovieTime,
    val ticketCount: Int,
    val theaterName: String,
)
