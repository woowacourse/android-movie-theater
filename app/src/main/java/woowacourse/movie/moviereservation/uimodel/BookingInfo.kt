package woowacourse.movie.moviereservation.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class BookingInfo(
    val screenMovieId: Long,
    val theaterId: Long,
    val count: Int,
    val dateTime: LocalDateTime,
) : Parcelable
