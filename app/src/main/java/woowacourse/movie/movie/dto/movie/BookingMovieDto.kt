package woowacourse.movie.movie.dto.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.movie.dto.seat.SeatsDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto

@Parcelize
data class BookingMovieDto(
    val movie: MovieDto,
    val date: MovieDateDto,
    val time: MovieTimeDto,
    val ticketCount: TicketCountDto,
    val seats: SeatsDto,
) : Parcelable
