package woowacourse.movie.data.movie

import woowacourse.movie.domain.model.tools.Movie

interface MovieData {
    fun findMovieById(movieId: Long): Movie?
}
