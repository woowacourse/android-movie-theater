package woowacourse.movie.data.repository

import woowacourse.movie.data.source.MovieDataSource
import woowacourse.movie.domain.model.Movie

class DefaultMovieRepository(
    private val movieDataSource: MovieDataSource,
) : MovieRepository {
    override fun loadMovie(movieId: Int): Movie {
        val movieData = movieDataSource.findById(movieId)
        val moviePosterData = movieDataSource.imageSrc2(movieId)

        return Movie(
            id = movieData.id,
            title = movieData.title,
            runningTime = movieData.runningTime,
            description = movieData.description,
            poster = moviePosterData.poster,
        )
    }
}
