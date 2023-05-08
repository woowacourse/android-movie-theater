package woowacourse.movie.presenter

import woowacourse.movie.contract.MovieAdapterContract
import woowacourse.movie.data.AdvertisementItemViewData
import woowacourse.movie.data.MovieListItemViewData
import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.repository.AdvertisementRepository
import woowacourse.movie.data.repository.MovieRepository
import woowacourse.movie.data.repository.TheaterRepository
import woowacourse.movie.domain.Theaters
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy
import woowacourse.movie.mapper.AdvertisementMapper.toView
import woowacourse.movie.mapper.MovieMapper.toDomain
import woowacourse.movie.mapper.MovieMapper.toView
import woowacourse.movie.mapper.TheatersMapper.toView

class MovieAdapterPresenter(
    override val view: MovieAdapterContract.View,
    private val movieRepository: MovieRepository = MovieRepository(),
    private val advertisementRepository: AdvertisementRepository = AdvertisementRepository(),
    private val theaterRepository: TheaterRepository = TheaterRepository()
) : MovieAdapterContract.Presenter {
    override fun setMovieList() {
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
