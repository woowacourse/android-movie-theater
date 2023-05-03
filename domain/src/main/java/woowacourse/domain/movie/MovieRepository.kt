package woowacourse.domain.movie

import woowacourse.data.movie.MovieDatabase
import woowacourse.data.movie.MovieEntity

object MovieRepository {
    fun getMovies(): List<Movie> {
        return MovieDatabase.movies.map { it.toMovie() }
    }

    fun getMovie(movieId: Long): Movie {
        return MovieDatabase.selectMovie(movieId).toMovie()
    }

    fun MovieEntity.toMovie(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            screeningPeriod = ScreeningPeriod(this.startDate, this.endDate),
            runningTime = this.runningTime,
            description = this.description,
        )
    }

    fun Movie.toMovieEntity(): MovieEntity {
        return MovieEntity(
            id = this.id,
            title = this.title,
            startDate = this.screeningPeriod.startDate.value,
            endDate = this.screeningPeriod.endDate.value,
            runningTime = this.runningTime,
            description = this.description,
        )
    }
}
