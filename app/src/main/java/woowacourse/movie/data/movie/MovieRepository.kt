package woowacourse.movie.data.movie

import woowacourse.movie.data.movie.dto.Movie
import java.lang.IllegalArgumentException

object MovieRepository {
    private val movies: MutableMap<Long, Movie> = mutableMapOf()
    private const val NOT_EXIST_ID_MOVIE_MESSAGE = "해당하는 아이디의 영화가 존재하지 않습니다."

    init {
        saveAllMovies(dummyMovieData)
    }

    fun saveMovie(movie: Movie) {
        movies[movie.id] = movie
    }

    fun saveAllMovies(movies: List<Movie>) {
        movies.forEach { saveMovie(it) }
    }

    fun getAllMovies(): List<Movie> {
        return movies.map { it.value }
    }

    fun getMovieById(id: Long): Movie {
        return movies[id] ?: throw IllegalArgumentException(NOT_EXIST_ID_MOVIE_MESSAGE)
    }
}
