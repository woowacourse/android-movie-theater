package woowacourse.movie.movie.dto.movie

import woowacourse.movie.movie.dto.seat.SeatsDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto

data class BookingMovieDto(
    val movie: MovieDto,
    val date: MovieDateDto,
    val time: MovieTimeDto,
    val ticketCount: TicketCountDto,
    val seats: SeatsDto,
) : java.io.Serializable
