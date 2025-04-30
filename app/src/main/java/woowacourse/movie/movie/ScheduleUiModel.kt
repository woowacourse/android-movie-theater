package woowacourse.movie.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
class ScheduleUiModel(
    val movie: MovieUiModel,
    val screeningTimes: List<LocalTime>,
) : Parcelable
