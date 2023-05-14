package woowacourse.movie.movielist.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.ticket.dto.TicketCountDto

@Parcelize
data class SeatMovieDto(
    val ticketCount: TicketCountDto,
    val movie: MovieDto,
    val movieDate: MovieDateDto,
    val movieTime: MovieTimeDto,
    val theaterName: String,
) : Parcelable
