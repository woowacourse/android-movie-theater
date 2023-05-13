package woowacourse.movie.ticket.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.dto.movie.MovieDateDto
import woowacourse.movie.dto.movie.MovieTimeDto
import woowacourse.movie.dto.ticket.TicketCountDto

@Parcelize
data class BookingMovieModel(
    val title: String,
    val date: MovieDateDto,
    val time: MovieTimeDto,
    val ticketCount: TicketCountDto,
    val seats: String,
    val theaterName: String,
    val price: Int = 0
) : Parcelable
