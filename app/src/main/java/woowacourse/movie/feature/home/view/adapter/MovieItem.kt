package woowacourse.movie.feature.home.view.adapter

import woowacourse.movie.feature.model.MovieUiModel

sealed class MovieItem(
    movieItemViewType: MovieItemViewType,
) {
    val viewType: MovieItemViewType = movieItemViewType
    abstract val id: Int

    data class Movie(
        override val id: Int,
        val value: MovieUiModel,
    ) : MovieItem(MovieItemViewType.MOVIE)

    data class Advertisement(
        override val id: Int,
    ) : MovieItem(MovieItemViewType.ADVERTISEMENT)

    companion object {
        fun from(movies: List<MovieUiModel>): List<MovieItem> {
            var id = 0
            val movieItems = mutableListOf<MovieItem>()

            movies.chunked(3).forEach { movieChunk ->
                movieChunk.forEach { movie -> movieItems.add(Movie(id++, movie)) }
                if (movieChunk.size == 3) movieItems.add(Advertisement(id++))
            }

            return movieItems
        }
    }
}
