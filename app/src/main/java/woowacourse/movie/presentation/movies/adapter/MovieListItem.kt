package woowacourse.movie.presentation.movies.adapter

import androidx.annotation.DrawableRes
import woowacourse.movie.domain.model.movie.Movie

sealed interface MovieListItem {
    data class MovieItem(
        val movie: Movie,
    ) : MovieListItem

    data class AdvertisementItem(
        @DrawableRes val id: Int,
    ) : MovieListItem
}
