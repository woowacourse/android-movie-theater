package woowacourse.movie.data.movie

import woowacourse.movie.data.movie.dto.Movie

interface MovieRepository {
    fun saveMovie(movie: Movie)

    fun saveAllMovies(movies: List<Movie>)

    fun getAllMovies(): List<Movie>

    fun getMovieById(id: Long): Movie
}
