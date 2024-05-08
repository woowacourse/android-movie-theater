package woowacourse.movie.moviedetail.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class BookingDetailUiModel(
    val count: HeadCountUiModel,
    val date: SelectedDateUiModel,
    val time: SelectedTimeUiModel,
) : Parcelable {
    constructor(count: Int, date: LocalDate, time: LocalTime) : this(
        HeadCountUiModel(count.toString()),
        SelectedDateUiModel(date = date),
        SelectedTimeUiModel(time = time),
    )

    fun updateCount(updatedCount: HeadCountUiModel): BookingDetailUiModel = BookingDetailUiModel(updatedCount, date, time)

    fun updateDate(
        position: Int,
        updatedDate: String,
    ): BookingDetailUiModel = BookingDetailUiModel(count, SelectedDateUiModel(position, updatedDate), time)

    fun updateTime(
        position: Int,
        updatedTime: String,
    ): BookingDetailUiModel = BookingDetailUiModel(count, date, SelectedTimeUiModel(position, updatedTime))
}
