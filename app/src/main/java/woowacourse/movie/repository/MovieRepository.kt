package woowacourse.movie.repository

import woowacourse.movie.model.MovieModel

interface MovieRepository {
    fun getAll(): List<MovieModel>
}
