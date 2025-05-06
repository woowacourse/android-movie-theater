package woowacourse.movie.feature.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScreeningUiModel(
    val movie: MovieUiModel = MovieUiModel(),
    val theaterName: String = "",
    val times: List<MovieTimeUiModel> = emptyList<MovieTimeUiModel>(),
) : Parcelable
