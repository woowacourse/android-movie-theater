package woowacourse.movie.mapper.movie

import domain.BookingMovie
import woowacourse.movie.ticket.model.BookingMovieModel
import woowacourse.movie.mapper.ticket.mapToTicketCount
import woowacourse.movie.mapper.ticket.mapToTicketCountDto

fun BookingMovieModel.mapToDomain(): BookingMovie {
    return BookingMovie(this.title, this.date.mapToMovieDate(), this.time.mapToMovieTime(), this.ticketCount.mapToTicketCount(), this.seats, this.theaterName, this.price)
}

fun BookingMovie.mapToUIModel(): BookingMovieModel {
    return BookingMovieModel(this.title, this.date.mapToMovieDateDto(), this.time.mapToMovieTimeDto(), this.ticketCount.mapToTicketCountDto(), this.seats, this.theaterName, this.price)
}
