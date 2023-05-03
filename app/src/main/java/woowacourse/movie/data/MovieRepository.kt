package woowacourse.movie.data

import woowacourse.movie.model.MovieState

interface MovieRepository {
    fun allMovies(): List<MovieState>
}
