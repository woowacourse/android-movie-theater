package woowacourse.movie.model.ui

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import java.time.LocalDate

data class Movie(
    val title: String,
    val releaseDate: LocalDate,
    val runningTime: Int,
    @DrawableRes val imgSrc: Int = R.drawable.movie_making_poster,
) : Movieitem(ITEM_VIEW_TYPE_MOVIE) {
    companion object {
        const val ITEM_VIEW_TYPE_MOVIE = 0
    }
}
