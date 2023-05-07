package woowacourse.movie.data.movie

import woowacourse.movie.uimodel.MovieListModel

interface MovieRepository {
    fun getData(): List<MovieListModel>
}
