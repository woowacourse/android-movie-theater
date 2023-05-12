package woowacourse.movie.presentation.model.theater

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.movieitem.ListItem

typealias PresentationTheater = Theater

@Parcelize
data class Theater(
    val name: String,
    val movieTimes: List<MovieTime>,
) : ListItem, Parcelable {
    @IgnoredOnParcel
    val movieTimesCount: Int = movieTimes.size

    override fun isAd(): Boolean = false
}
