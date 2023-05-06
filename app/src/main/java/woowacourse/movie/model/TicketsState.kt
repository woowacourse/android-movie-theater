package woowacourse.movie.model

import android.os.Parcelable
import java.time.LocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketsState(
    val cinemaName: String,
    val movieName: String,
    val dateTime: LocalDateTime,
    val positions: List<SeatPositionState>
) : Parcelable
