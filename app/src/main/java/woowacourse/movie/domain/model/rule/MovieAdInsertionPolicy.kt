package woowacourse.movie.domain.model.rule

import woowacourse.movie.domain.model.movie.MovieContent

class MovieAdInsertionPolicy(
    private val frequency: Int = DEFAULT_FREQUENCY,
) : AdInsertionPolicy<MovieContent, MovieContent.MovieAd> {
    override fun insert(
        items: List<MovieContent>,
        adFactory: () -> MovieContent.MovieAd,
    ): List<MovieContent> =
        items
            .chunked(frequency)
            .flatMap { chunk ->
                if (chunk.isFullChunk(frequency)) chunk + adFactory() else chunk
            }

    private fun List<MovieContent>.isFullChunk(frequency: Int) = this.size == frequency

    companion object {
        private const val DEFAULT_FREQUENCY = 3
    }
}
