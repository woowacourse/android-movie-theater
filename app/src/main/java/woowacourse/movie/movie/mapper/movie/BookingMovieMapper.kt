package woowacourse.movie.movie.mapper.movie

import domain.BookingMovie
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.mapper.ticket.mapToTicketCount
import woowacourse.movie.movie.mapper.ticket.mapToTicketCountDto

fun BookingMovieEntity.mapToDomain(): BookingMovie {
    return BookingMovie(this.title, this.date.mapToMovieDate(), this.time.mapToMovieTime(), this.ticketCount.mapToTicketCount(), this.seats, this.theaterName, this.price)
}

fun BookingMovie.mapToUIModel(): BookingMovieEntity {
    return BookingMovieEntity(this.title, this.date.mapToMovieDateDto(), this.time.mapToMovieTimeDto(), this.ticketCount.mapToTicketCountDto(), this.seats, this.theaterName, this.price)
}
