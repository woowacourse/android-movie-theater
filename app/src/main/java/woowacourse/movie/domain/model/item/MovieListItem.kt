package woowacourse.movie.domain.model.item

import woowacourse.movie.domain.model.movie.Movie

sealed class MovieListItem {
    data class MovieItem(
        val movie: Movie,
    ) : MovieListItem()

    data class AdItem(
        val advertisement: Advertisement,
    ) : MovieListItem()
}
