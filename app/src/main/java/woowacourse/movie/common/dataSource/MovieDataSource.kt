package woowacourse.movie.dataSource

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.domain.Movie

class MovieDataSource(private val movieDao: MovieDao) : DataSource<Movie> {
    private val data: MutableList<Movie> = movieDao.selectAllMovies().toMutableList()
    override val value: List<Movie>
        get() = data

    override fun add(t: Movie) {
        data.add(t)
        movieDao.insertMovie(movieDao.writableDatabase, t)
    }
}
