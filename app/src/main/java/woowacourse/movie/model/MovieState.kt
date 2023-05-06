package woowacourse.movie.model

import android.net.Uri
import android.os.Parcelable
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieState(
    val imgUri: Uri,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val screeningTimes: List<LocalTime>,
    val runningTime: Int,
    val description: String
) : Parcelable
