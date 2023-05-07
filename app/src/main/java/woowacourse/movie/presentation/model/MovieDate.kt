package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDate(
    val year: Int,
    val month: Int,
    val day: Int,
) : Parcelable {

    companion object {
        fun from(movieDate: String): MovieDate {
            val date = movieDate.split(". ").map { it.toInt() }
            return MovieDate(date[0], date[1], date[2])
        }
    }
}
