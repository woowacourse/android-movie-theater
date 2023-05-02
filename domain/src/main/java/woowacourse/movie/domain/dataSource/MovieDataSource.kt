package woowacourse.movie.domain.dataSource

import woowacourse.movie.domain.Movie

interface MovieDataSource {
    fun getData(): List<Movie>
    fun addData(data: Movie)
}
