package woowacourse.movie.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TheaterUiModel(
    val place: String,
    val schedule: ScheduleUiModel,
) : Parcelable
