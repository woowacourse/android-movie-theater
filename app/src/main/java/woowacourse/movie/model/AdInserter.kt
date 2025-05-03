package woowacourse.movie.model

object AdInserter {
    private const val AD_INSERT_INTERVAL: Int = 3
    private const val INDEX_OFFSET: Int = 1

    fun insertAd(movies: List<Movie>): List<MovieFeed> {
        val result: MutableList<MovieFeed> = mutableListOf()

        movies.map { MovieFeed.MovieItem(it) }.forEachIndexed { index, movieItem ->
            result.add(movieItem)
            if ((index + INDEX_OFFSET) % AD_INSERT_INTERVAL == 0) {
                result.add(MovieFeed.AdvertisementItem(0))
            }
        }

        return result
    }
}
