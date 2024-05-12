package woowacourse.movie.data.movie

import woowacourse.movie.data.movie.dto.Movie
import java.lang.IllegalArgumentException

object MovieRepositoryImpl : MovieRepository {
    private val movies: MutableMap<Long, Movie> = mutableMapOf()
    private const val NOT_EXIST_ID_MOVIE_MESSAGE = "해당하는 아이디의 영화가 존재하지 않습니다."

    init {
        saveAllMovies(dummyMovieData)
    }

    override fun saveMovie(movie: Movie) {
        movies[movie.id] = movie
    }

    override fun saveAllMovies(movies: List<Movie>) {
        movies.forEach { saveMovie(it) }
    }

    override fun getAllMovies(): List<Movie> {
        return movies.map { it.value }
    }

    override fun getMovieById(id: Long): Movie {
        return movies[id] ?: throw IllegalArgumentException(NOT_EXIST_ID_MOVIE_MESSAGE)
    }
}
