package woowacourse.movie.domain

sealed class MovieItem {
    abstract val viewType: Int

    data class ItemMovie(val movie: Movie) : MovieItem() {
        override val viewType: Int = TYPE_MOVIE
    }

    data object ItemAd : MovieItem() {
        override val viewType: Int = TYPE_AD
    }

    companion object {
        const val TYPE_MOVIE = 0
        const val TYPE_AD = 1
    }
}
