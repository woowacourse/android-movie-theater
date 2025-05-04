package woowacourse.movie.presentation.movies.adapter

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.domain.model.Movie

sealed class MovieListItem(
    val type: ViewType,
) {
    data class MovieItem(
        val movie: Movie,
    ) : MovieListItem(ViewType.TYPE_MOVIE)

    data class AdsItem(
        @DrawableRes val id: Int = R.drawable.advertisement,
    ) : MovieListItem(ViewType.TYPE_ADS)

    enum class ViewType {
        TYPE_MOVIE,
        TYPE_ADS,
    }
}
