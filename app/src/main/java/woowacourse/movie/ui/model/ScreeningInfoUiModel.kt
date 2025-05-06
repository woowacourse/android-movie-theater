package woowacourse.movie.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
class ScreeningInfoUiModel(
    val movie: MovieUiModel,
    val screeningTimes: List<LocalTime>,
) : Parcelable
