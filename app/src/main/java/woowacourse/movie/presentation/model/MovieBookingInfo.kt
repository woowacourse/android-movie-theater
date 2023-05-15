package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieBookingInfo(
    val movieInfo: Movie,
    val date: String,
    val time: String,
    val ticketCount: Int,
    val theaterName: String
) : Parcelable
