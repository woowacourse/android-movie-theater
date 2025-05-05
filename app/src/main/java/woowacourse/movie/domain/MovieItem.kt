package woowacourse.movie.domain

sealed class MovieItem {
    data class ItemMovie(val movie: Movie) : MovieItem()

    data object ItemAd : MovieItem()
}
