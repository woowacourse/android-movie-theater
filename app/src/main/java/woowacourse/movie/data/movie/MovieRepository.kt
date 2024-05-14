package woowacourse.movie.data.movie

import woowacourse.movie.data.movie.dto.Movie

interface MovieRepository {
    fun save(movie: Movie)

    fun saveAll(movies: List<Movie>)

    fun findAll(): List<Movie>

    fun find(id: Long): Movie
}
