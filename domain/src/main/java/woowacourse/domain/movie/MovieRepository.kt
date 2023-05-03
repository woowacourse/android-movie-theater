package woowacourse.domain.movie

interface MovieRepository {
    fun getMovies(): List<Movie>

    fun getMovie(movieId: Long): Movie?
}
