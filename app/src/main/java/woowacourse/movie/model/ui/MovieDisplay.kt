package woowacourse.movie.model.ui

import androidx.annotation.DrawableRes
import woowacourse.movie.R

data class MovieItemDisplay(
    val title: String,
    val releaseDate: String,
    val runningTime: String,
    @DrawableRes val imgSrc: Int = R.drawable.movie_making_poster
) : MovieDisplay(ITEM_VIEW_TYPE_MOVIE) {
    companion object {
        const val ITEM_VIEW_TYPE_MOVIE = 0
    }
}
