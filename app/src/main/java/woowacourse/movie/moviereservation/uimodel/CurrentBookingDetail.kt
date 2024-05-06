package woowacourse.movie.moviereservation.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentBookingDetail(
    val count: Int,
    val datePosition: Int = 0,
    val timePosition: Int = 0,
) : Parcelable {
    fun updateCount(updatedCount: Int): CurrentBookingDetail = copy(count = updatedCount)

    fun updateDate(updatedDate: Int): CurrentBookingDetail = copy(datePosition = updatedDate)

    fun updateTime(updatedTime: Int): CurrentBookingDetail = copy(timePosition = updatedTime)
}
