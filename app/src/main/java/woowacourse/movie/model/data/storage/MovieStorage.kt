package woowacourse.movie.model.data.storage

import woowacourse.movie.domain.model.tools.Movie

interface MovieStorage {
    fun getMovieById(id: Long): Movie

    fun getMovies(): List<Movie>
}
