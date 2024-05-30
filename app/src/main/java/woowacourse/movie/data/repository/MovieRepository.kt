package woowacourse.movie.data.repository

import woowacourse.movie.domain.model.Movie

interface MovieRepository {
    fun loadMovie(movieId: Int): Movie
}
