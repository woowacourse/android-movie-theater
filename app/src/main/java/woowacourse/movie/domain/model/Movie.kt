package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val poster: String,
    val title: String,
    val screeningPeriods: List<ScreeningPeriod>,
    val runningTime: RunningTime,
    val cinemas: List<Cinema>
) : Parcelable
