package woowacourse.movie.common.repository

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.dataSource.DataSource
import woowacourse.movie.dataSource.MovieDataSource
import woowacourse.movie.dataSource.TheaterDataSource
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
