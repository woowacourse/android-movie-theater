package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class MovieUiModel(
    val title: String,
    val screeningDateTimes: Map<LocalDate, List<LocalTime>>,
    val runningTime: Int,
    val posterResourceId: Int,
    val summary: String,
) : Parcelable
