package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.MovieDataSource
import woowacourse.movie.data.dataSource.TheaterDataSource
import woowacourse.movie.data.database.MovieDao
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.Theaters

class TheaterRepository(movieDao: MovieDao) {
    private val theaterDataSource: DataSource<Theater> = TheaterDataSource(
        MovieDataSource(movieDao)
    )

    fun requestTheaters(): Theaters {
        return Theaters(theaterDataSource.value)
    }
}
