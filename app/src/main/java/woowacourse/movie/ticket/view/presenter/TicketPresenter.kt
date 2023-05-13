package woowacourse.movie.ticket.view.presenter

import domain.BookingMovie
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.mapper.movie.mapToDomain
import woowacourse.movie.mapper.movie.mapToUIModel
import woowacourse.movie.ticket.view.contract.TicketContract

class TicketPresenter(private val view: TicketContract.View) : TicketContract.Presenter {
    override lateinit var bookingMovie: BookingMovie

    override fun getData(data: BookingMovieModel) {
        bookingMovie = data.mapToDomain()
    }

    override fun updateTicketInfo(){
        view.showTicketInfo(bookingMovie.mapToUIModel())
    }
}
