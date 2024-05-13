package woowacourse.movie.data.movie

import woowacourse.movie.data.movie.dto.Movie
import java.lang.IllegalArgumentException

object MovieRepositoryImpl : MovieRepository {
    private val movies: MutableMap<Long, Movie> = mutableMapOf()
    private const val NOT_EXIST_ID_MOVIE_MESSAGE = "해당하는 아이디의 영화가 존재하지 않습니다."

    init {
        saveAll(dummyMovieData)
    }

    override fun save(movie: Movie) {
        movies[movie.id] = movie
    }

    override fun saveAll(movies: List<Movie>) {
        movies.forEach { save(it) }
    }

    override fun findAll(): List<Movie> {
        return movies.map { it.value }
    }

    override fun find(id: Long): Movie {
        return movies[id] ?: throw IllegalArgumentException(NOT_EXIST_ID_MOVIE_MESSAGE)
    }
}
