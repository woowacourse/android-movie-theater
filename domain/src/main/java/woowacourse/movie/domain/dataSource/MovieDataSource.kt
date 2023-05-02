package woowacourse.movie.domain.dataSource

import woowacourse.movie.domain.model.Movie

interface MovieDataSource {
    fun getData(): List<Movie>
    fun addData(data: Movie)
}
