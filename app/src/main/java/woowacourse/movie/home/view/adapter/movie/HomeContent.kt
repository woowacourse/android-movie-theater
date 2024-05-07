package woowacourse.movie.home.view.adapter.movie

import androidx.annotation.DrawableRes
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater

sealed class HomeContent(open val viewType: Int) {
    data class Movie(
        val id: Long,
        @DrawableRes
        val image: Int,
        val title: String,
        val description: String,
        val date: MovieDate,
        val runningTime: Int,
        val theaters: List<Theater>,
        override val viewType: Int = MOVIE_VIEW_TYPE,
    ) : HomeContent(viewType)

    data class Advertisement(
        @DrawableRes
        val banner: Int,
        val link: String,
        override val viewType: Int = ADVERTISEMENT_VIEW_TYPE,
    ) : HomeContent(viewType)

    companion object {
        private const val MOVIE_VIEW_TYPE = 0
        private const val ADVERTISEMENT_VIEW_TYPE = 1
    }
}
