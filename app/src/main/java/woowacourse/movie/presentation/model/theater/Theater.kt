package woowacourse.movie.presentation.model.theater

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.MovieTime

typealias PresentationTheater = Theater

@Parcelize
data class Theater(
    val name: String,
    val movieTimes: List<MovieTime>,
) : Parcelable {
    @IgnoredOnParcel
    val movieTimesCount: Int = movieTimes.size
}
