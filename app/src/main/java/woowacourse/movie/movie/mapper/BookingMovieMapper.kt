package woowacourse.movie.movie.mapper

import domain.BookingMovie
import domain.movieinfo.MovieDate
import woowacourse.movie.dto.MovieDateDto
import woowacourse.movie.mapper.mapToMovie
import woowacourse.movie.mapper.mapToMovieDate
import woowacourse.movie.mapper.mapToMovieDateDto
import woowacourse.movie.mapper.mapToMovieDto
import woowacourse.movie.mapper.mapToMovieTime
import woowacourse.movie.mapper.mapToMovieTimeDto
import woowacourse.movie.mapper.mapToSeats
import woowacourse.movie.mapper.mapToSeatsDto
import woowacourse.movie.mapper.mapToTicketCount
import woowacourse.movie.mapper.mapToTicketCountDto
import woowacourse.movie.movie.dto.BookingMovieDto

fun BookingMovieDto.mapToDomain(): BookingMovie {
    return BookingMovie(this.movie.mapToMovie(), this.date.mapToMovieDate(), this.time.mapToMovieTime(), this.ticketCount.mapToTicketCount(), this.seats.mapToSeats())
}

fun BookingMovie.mapToUIModel(): BookingMovieDto {
    return BookingMovieDto(this.movie.mapToMovieDto(), this.date.mapToMovieDateDto(), this.time.mapToMovieTimeDto(), this.ticketCount.mapToTicketCountDto(), this.seats.mapToSeatsDto())
}
