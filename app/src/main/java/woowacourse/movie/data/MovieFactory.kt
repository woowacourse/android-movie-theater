package woowacourse.movie.data

import woowacourse.movie.domain.Movie
import woowacourse.movie.movie.FeedItem

class MovieFactory(private val movies: List<Movie>) {
    val items get() = createFeedItems()

    private fun createFeedItems(): List<FeedItem> {
        val result = mutableListOf<FeedItem>()
        movies.forEachIndexed { index, movie ->
            result.add(FeedItem.MovieItem(movie))
            if ((index + 1) % 3 == 0) {
                result.add(FeedItem.AdvertiseItem)
            }
        }
        return result.toList()
    }
}
