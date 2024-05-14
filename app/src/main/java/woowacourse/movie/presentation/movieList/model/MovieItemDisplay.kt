package woowacourse.movie.presentation.movieList.model

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import java.time.LocalDate

data class MovieItemDisplay(
    val title: String,
    val releaseDate: LocalDate,
    val runningTime: Int,
    @DrawableRes val imgSrc: Int = R.drawable.movie_making_poster,
) : MovieDisplay(ITEM_VIEW_TYPE_MOVIE) {
    companion object {
        const val ITEM_VIEW_TYPE_MOVIE = 0
    }
}
