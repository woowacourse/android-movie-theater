package woowacourse.movie.model

sealed interface MovieFeed {
    data class MovieItem(val movie: Movie) : MovieFeed

    data class AdvertisementItem(val id: Int) : MovieFeed
}
