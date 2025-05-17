package woowacourse.movie.data.repository

import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.entity.MovieEntity

class MovieRepositoryImpl(
    private val movieDao: MovieDao,
) : MovieRepository {
    override fun insertAll(vararg movieEntities: MovieEntity) {
        movieDao.insertAll(*movieEntities)
    }

    override fun getAll(): List<MovieEntity> = movieDao.getAll()
}
