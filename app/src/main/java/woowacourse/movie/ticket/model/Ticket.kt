package woowacourse.movie.ticket.model

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.detail.model.Count
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.seats.model.Seat

data class Ticket(
    val price: Int,
    val seats: List<Seat>,
    val ticketCount: Count,
    val screeningDate: String,
    val screeningTime: String,
    val movieId: Long,
    val theaterId: Long,
) {
    val title
        get() = MovieDataSource.movieList.first { it.id == movieId }.title
    val theater
        get() = TheaterData.theaters.first { it.id == theaterId }.name
}
