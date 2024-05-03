package woowacourse.movie.moviereservation.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Parcelize
data class BookingInfoUiModel(
    val screenMovieId: Long,
    val theaterId: Long,
    val count: HeadCountUiModel,
    val date: SelectedDateUiModel,
    val time: SelectedTimeUiModel,
) : Parcelable {
    constructor(
        screenMovieId: Long,
        theaterId: Long,
        count: Int,
        date: LocalDate,
        time: LocalTime,
    ) : this(
        screenMovieId,
        theaterId,
        HeadCountUiModel(count.toString()),
        SelectedDateUiModel(date = date),
        SelectedTimeUiModel(time = time),
    )

    constructor(screenMovieId: Long, theaterId: Long, bookingDetailUiModel: BookingDetailUiModel) : this(
        screenMovieId,
        theaterId,
        bookingDetailUiModel.count,
        bookingDetailUiModel.date,
        bookingDetailUiModel.time,
    )

    fun localDateTime() = LocalDateTime.of(date.getLocalDate(), time.getLocalTime())

    fun maxSelectSize() = count.count.toInt()
}
