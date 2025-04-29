package woowacourse.movie.movie

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TheaterUiModel(
    val place: String,
    val movies: List<MovieUiModel>,
) : Parcelable
