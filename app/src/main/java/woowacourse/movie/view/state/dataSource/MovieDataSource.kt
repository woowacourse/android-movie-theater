package woowacourse.movie.view.state.dataSource

import woowacourse.movie.domain.MovieMock
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.mapper.MovieMapper.toView

class MovieDataSource : DataSource<MovieViewData> {
    override val value: List<MovieViewData>
        get() = data

    override fun add(t: MovieViewData) {
        data.add(t)
    }

    companion object {
        private val data: MutableList<MovieViewData> =
            List(2500) { MovieMock.createMovies() }.flatten().map { it.toView() }.toMutableList()
    }
}
