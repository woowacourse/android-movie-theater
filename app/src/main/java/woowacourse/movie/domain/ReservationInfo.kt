package woowacourse.movie.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationInfo(
    val title: String,
    val date: String,
    val time: String,
    val personnel: Int,
    val seats: String,
    val theater: String,
    val price: Int,
): Parcelable
