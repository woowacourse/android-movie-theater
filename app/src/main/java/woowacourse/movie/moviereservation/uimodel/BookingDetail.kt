package woowacourse.movie.moviereservation.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class BookingDetail(
    val count: HeadCountUiModel,
    val date: SelectedDateUiModel,
    val time: SelectedTimeUiModel,
):Parcelable {

    constructor(count: Int, date: LocalDate, time: LocalTime) : this(
        HeadCountUiModel(count.toString()),
        SelectedDateUiModel(date = date),
        SelectedTimeUiModel(time = time),
    )

    fun updateCount(updatedCount: HeadCountUiModel): BookingDetail = BookingDetail(updatedCount, date, time)

    fun updateCount(updatedCount: Int): BookingDetail = BookingDetail(HeadCountUiModel(updatedCount), date, time)

    fun updateDate(updatedDate: SelectedDateUiModel): BookingDetail = BookingDetail( count, updatedDate, time)

    fun updateDate(
        position: Int,
        updatedDate: String,
    ): BookingDetail = BookingDetail( count, SelectedDateUiModel(position, updatedDate), time)

    fun updateTime(updatedTime: SelectedTimeUiModel): BookingDetail = BookingDetail( count, date, updatedTime)

    fun updateTime(
        position: Int,
        updatedTime: String,
    ): BookingDetail = BookingDetail(count, date, SelectedTimeUiModel(position, updatedTime))

}
