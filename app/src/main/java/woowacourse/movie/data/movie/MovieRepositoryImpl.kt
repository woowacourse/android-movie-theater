package woowacourse.movie.data.movie

import woowacourse.movie.uimodel.MovieListModel
import woowacourse.movie.utils.MockData

class MovieRepositoryImpl : MovieRepository {
    override fun getData(): List<MovieListModel> {
        return MockData.getMoviesWithAds()
    }
}
