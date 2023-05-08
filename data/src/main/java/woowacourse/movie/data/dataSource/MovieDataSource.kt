package woowacourse.movie.data.dataSource

import woowacourse.movie.data.database.MovieDao
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
