package woowacourse.movie.presenter

import woowacourse.movie.contract.MoviesContract
import woowacourse.movie.mock.MockAdvertisementFactory
import woowacourse.movie.mock.MockMoviesFactory
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.mapper.MovieMapper.toUi

class MoviesPresenter(val view: MoviesContract.View) : MoviesContract.Presenter {
    override fun onMovieItemClick(movieUiModel: MovieUiModel) {
        view.startMovieReservationActivity(movieUiModel = movieUiModel)
    }

    override fun onAdvertisementItemClick(advertisementUiModel: AdvertisementUiModel) {
        view.startAdvertisementUrl(advertisementUiModel.url)
    }

    override fun updateMovieList() {
        val movies = MockMoviesFactory.makeMovies()
        val movieUiModels = movies.value.map { it.toUi() }
        val advertisementUiModel = MockAdvertisementFactory.generateAdvertisement()
        view.setAdapter(movieUiModels, advertisementUiModel)
    }
}
