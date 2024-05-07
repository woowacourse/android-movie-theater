package woowacourse.movie.model.movie

import androidx.annotation.DrawableRes
import java.time.LocalDate

data class Movie(
    val id: Int,
    @DrawableRes
    val posterId: Int,
    val title: String,
    val screeningPeriod: List<LocalDate>,
    val runningTime: String,
    val summary: String,
) {
    companion object {
        const val DEFAULT_MOVIE_ID = -1
    }
}
