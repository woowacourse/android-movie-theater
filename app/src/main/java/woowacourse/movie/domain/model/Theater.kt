package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Theater(
    val name: String = "",
    val times: List<MovieTime> = emptyList<MovieTime>(),
) : Parcelable {
    val timesCount: Int get() = times.size
}
