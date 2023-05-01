package woowacourse.movie.movie

object MovieRepository {
    fun getMovies(): List<Movie> {
        return MovieDatabase.movies.map { it.toMovie() }
    }

    fun getMovie(movieId: Long): Movie {
        return MovieDatabase.selectMovie(movieId).toMovie()
    }

    private fun MovieEntity.toMovie(): Movie {
        return Movie(
            id = this.id,
            title = this.title,
            screeningPeriod = ScreeningPeriod(this.startDate, this.endDate),
            runningTime = this.runningTime,
            description = this.description,
        )
    }
}
