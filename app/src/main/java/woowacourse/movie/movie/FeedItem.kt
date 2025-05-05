package woowacourse.movie.movie

import woowacourse.movie.domain.Movie

sealed class FeedItem {
    data class MovieItem(val movie: Movie) : FeedItem()

    data object AdvertiseItem : FeedItem()
}
