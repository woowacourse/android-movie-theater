package woowacourse.movie.repository

import woowacourse.movie.data.mock.MockAdvertisementFactory
import woowacourse.movie.data.mock.MockMoviesFactory

object MoviesRepositoryImpl : MoviesRepository {
    override val movies = MockMoviesFactory.makeMovies()
    override val advertisements = MockAdvertisementFactory.generateAdvertisement()
}
