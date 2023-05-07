package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTime(val hour: Int, val min: Int = DEFAULT_MIN) : Parcelable {
    companion object {
        private const val DEFAULT_MIN = 0

        fun from(movieTime: String): MovieTime {
            val time = movieTime.split(":").map { it.toInt() }
            return MovieTime(time[0], time[1])
        }
    }
}
