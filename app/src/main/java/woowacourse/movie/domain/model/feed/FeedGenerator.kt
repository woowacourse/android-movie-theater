package woowacourse.movie.domain.model.feed

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.feed.Feed.Advertisement

class FeedGenerator {
    fun generate(): List<Feed> =
        buildList {
            MovieStore().movies.forEachIndexed { index, movie ->
                add(movie)
                if ((index + 1) % AD_INTERVAL == 0) add(Advertisement())
            }
        }

    companion object {
        private const val AD_INTERVAL = 3
    }
}
