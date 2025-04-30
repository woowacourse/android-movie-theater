package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Screening(
    val movie: Movie,
    val theater: Theater,
    val times: List<MovieTime>,
) : Parcelable {
    val timesCount: Int get() = times.size
}
