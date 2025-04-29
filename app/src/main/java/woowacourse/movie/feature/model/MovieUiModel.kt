package woowacourse.movie.feature.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Theater

@Parcelize
data class MovieUiModel(
    val title: String = "",
    val startDate: MovieDateUiModel = MovieDateUiModel(),
    val endDate: MovieDateUiModel = MovieDateUiModel(),
    val runningTime: Int = 0,
    @DrawableRes val poster: Int = 0,
    val availableTheaters: List<Theater> = emptyList<Theater>(),
) : Parcelable
