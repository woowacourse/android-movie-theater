package woowacourse.movie.dto.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.dto.ticket.TicketCountDto

@Parcelize
data class SeatMovieDto(
    val ticketCount: TicketCountDto,
    val movie: MovieDto,
    val movieDate: MovieDateDto,
    val movieTime: MovieTimeDto,
    val theaterName: String,
) : Parcelable
