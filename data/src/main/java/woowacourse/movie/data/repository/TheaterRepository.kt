package woowacourse.movie.data.repository

import woowacourse.movie.data.dataSource.DataSource
import woowacourse.movie.data.dataSource.TheaterDataSource
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.Theaters

class TheaterRepository {
    private val theaterDataSource: DataSource<Theater> = TheaterDataSource(
        MovieRepository().requestMovies()
    )

    fun requestTheaters(): Theaters {
        return Theaters(theaterDataSource.value)
    }
}
