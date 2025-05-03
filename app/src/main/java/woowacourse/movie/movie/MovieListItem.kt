package woowacourse.movie.movie

import woowacourse.movie.ui.model.MovieUiModel

sealed interface MovieListItem {
    data class MovieItem(val movie: MovieUiModel) : MovieListItem

    data class AdvertisementItem(val imageId: Int) : MovieListItem
}
