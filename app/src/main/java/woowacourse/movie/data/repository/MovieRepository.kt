package woowacourse.movie.data.repository

import woowacourse.movie.data.entity.MovieEntity

interface MovieRepository {
    fun insertAll(vararg movieEntities: MovieEntity)

    fun getAll(): List<MovieEntity>
}
