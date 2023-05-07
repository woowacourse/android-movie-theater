package woowacourse.domain.movie

interface MovieRepository {
    fun getMovies(): List<Movie>

    fun getMovie(movieId: Long): Movie?

    fun addMovie(
        title: String,
        screeningPeriod: ScreeningPeriod,
        runningTime: Int,
        description: String,
    )
}
