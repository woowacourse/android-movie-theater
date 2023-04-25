package woowacourse.movie.movie.dto

import woowacourse.movie.dto.MovieDateDto
import woowacourse.movie.dto.MovieDto
import woowacourse.movie.dto.MovieTimeDto
import woowacourse.movie.dto.SeatsDto
import woowacourse.movie.dto.TicketCountDto

data class BookingMovieDto(
    val movie: MovieDto,
    val date: MovieDateDto,
    val time: MovieTimeDto,
    val ticketCount: TicketCountDto,
    val seats: SeatsDto,
) : java.io.Serializable
