package woowacourse.movie.movieList

import woowacourse.movie.common.database.MovieDao
import woowacourse.movie.common.mapper.AdvertisementMapper.toView
import woowacourse.movie.common.mapper.MovieMapper.toDomain
import woowacourse.movie.common.mapper.MovieMapper.toView
import woowacourse.movie.common.mapper.TheatersMapper.toView
import woowacourse.movie.common.model.AdvertisementItemViewData
import woowacourse.movie.common.model.MovieListItemViewData
import woowacourse.movie.common.model.MovieListItemsViewData
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.repository.AdvertisementRepository
import woowacourse.movie.common.repository.MovieRepository
import woowacourse.movie.common.repository.TheaterRepository
import woowacourse.movie.domain.Theaters
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy

class MovieAdapterPresenter(
    override val view: MovieAdapterContract.View,
    movieDao: MovieDao,
    private val movieRepository: MovieRepository = MovieRepository(movieDao),
    private val advertisementRepository: AdvertisementRepository = AdvertisementRepository(),
    private val theaterRepository: TheaterRepository = TheaterRepository(movieDao)
) : MovieAdapterContract.Presenter {
    init {
        val movieCount = requestAdvertisementPolicy().movieCount
        val movies = MovieListItemsViewData.from(
            requestMovies(), requestAdvertisements(), movieCount
        )
        view.setAdapterData(movies)
    }

    override fun makeTheaterDialog(movie: MovieListItemViewData) {
        val movie = movie as? MovieViewData ?: return
        val theaters = requestTheaters()
        val movieTheaters = theaters.findTheaterByMovie(movie.toDomain()).toView()
        view.onClickItem(movie, movieTheaters)
    }

    private fun requestMovies(): List<MovieViewData> =
        movieRepository.requestMovies().map { it.toView() }

    private fun requestAdvertisements(): List<AdvertisementItemViewData> =
        advertisementRepository.requestAdvertisements().map { it.toView() }

    private fun requestAdvertisementPolicy(): AdvertisementPolicy =
        advertisementRepository.requestAdvertisementPolicy()

    private fun requestTheaters(): Theaters = theaterRepository.requestTheaters()
}
