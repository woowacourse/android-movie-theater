package woowacourse.movie.ticket.presenter

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.detail.model.Count
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.model.Ticket
import woowacourse.movie.ticket.model.TicketDataResource

class MovieTicketPresenter(
    val view: MovieTicketContract.View,
) : MovieTicketContract.Presenter {

    override fun storeTicketData(
        movieId: Long,
        screeningDate: String,
        screeningTime: String,
        seatsCount: Count,
        seats: List<Seat>,
        theaterId: Long,
        price: Int,
    ) {
        val movieTitle = MovieDataSource.movieList.first { it.id == movieId }.title
        val seatsFormatted = seats.joinToString { it.coordinate }
        val theaterName = TheaterData.theaters.first { it.id == theaterId }.name
        TicketDataResource.ticket =
            Ticket(
                movieTitle,
                screeningDate,
                screeningTime,
                seatsCount.number,
                seatsFormatted,
                theaterName,
                price,
            )
    }

    override fun setTicketInfo() {
        view.showTicketView(
            TicketDataResource.ticket.movieTitle,
            TicketDataResource.ticket.screeningDate,
            TicketDataResource.ticket.screeningTime,
            TicketDataResource.ticket.seatsCount,
            TicketDataResource.ticket.seats,
            TicketDataResource.ticket.theaterName,
            TicketDataResource.ticket.price,
        )
    }
}
