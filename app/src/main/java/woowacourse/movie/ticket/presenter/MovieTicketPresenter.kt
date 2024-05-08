package woowacourse.movie.ticket.presenter

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.detail.model.Count
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.seats.model.Seat
import woowacourse.movie.ticket.contract.MovieTicketContract
import woowacourse.movie.ticket.db_mapper.DbMapper
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
        TicketDataResource.ticket =
            Ticket(
                movieId,
                screeningDate,
                screeningTime,
                seatsCount,
                seats,
                theaterId,
                price,
            )

        TicketDataResource.dbTicket = DbMapper.mapTicketDb(TicketDataResource.ticket)
    }

    override fun setTicketInfo() {
        val movieTitle = MovieDataSource.movieList.first { it.id == TicketDataResource.ticket.movieId }.title
        val theaterName = TheaterData.theaters.first { it.id == TicketDataResource.ticket.theaterId }.name
        view.showTicketView(
            movieTitle,
            TicketDataResource.ticket.screeningDate,
            TicketDataResource.ticket.screeningTime,
            TicketDataResource.ticket.seatsCount,
            TicketDataResource.ticket.seats,
            theaterName,
            TicketDataResource.ticket.price,
        )
    }
}
