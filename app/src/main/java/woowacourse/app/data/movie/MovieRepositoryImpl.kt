package woowacourse.app.data.movie

import woowacourse.app.data.movie.MovieMapper.toMovie
import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.MovieRepository
import woowacourse.domain.movie.ScreeningPeriod
import woowacourse.domain.util.CgvError.DataSourceError.DataSourceNoSuchId
import woowacourse.domain.util.CgvResult
import woowacourse.domain.util.CgvResult.Failure
import woowacourse.domain.util.CgvResult.Success

class MovieRepositoryImpl(private val movieDataSource: MovieDataSource) : MovieRepository {
    override fun getMovies(): List<Movie> {
        return movieDataSource.getMovieEntities().map { it.toMovie() }
    }

    override fun getMovie(movieId: Long): CgvResult<Movie> {
        val movie: Movie = movieDataSource.getMovieEntity(movieId)?.toMovie()
            ?: return Failure(DataSourceNoSuchId())
        return Success(movie)
    }

    override fun addMovie(
        title: String,
        screeningPeriod: ScreeningPeriod,
        runningTime: Int,
        description: String,
    ) {
        movieDataSource.addMovieEntity(
            title = title,
            startDate = screeningPeriod.startDate.value,
            endDate = screeningPeriod.endDate.value,
            runningTime = runningTime,
            description = description,
        )
    }
}
