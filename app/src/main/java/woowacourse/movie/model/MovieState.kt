package woowacourse.movie.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieState(
    @DrawableRes
    val imgId: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val screeningTimes: List<LocalTime>,
    val runningTime: Int,
    val description: String
) : Parcelable
