package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.MovieDatabase.movies
import java.time.LocalDate

@Parcelize
data class Movie(
    val title: String,
    val poster: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
) : Parcelable {
    companion object {
        val values: List<Movie> =
            List(1111) {
                movies.values
            }.flatten()
    }
}
