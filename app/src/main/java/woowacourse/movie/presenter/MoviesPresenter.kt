package woowacourse.movie.presenter

import woowacourse.movie.contract.MoviesContract
import woowacourse.movie.mock.MockAdvertisementFactory
import woowacourse.movie.mock.MockMoviesFactory
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.mapper.MovieMapper.toUi

class MoviesPresenter(val view: MoviesContract.View) : MoviesContract.Presenter {
    private lateinit var movieUiModel: MovieUiModel
    override fun onMovieItemClick(clickedMovie: MovieUiModel) {
        movieUiModel = clickedMovie
        view.showBottomSheet(movieUiModel.theaters)
    }

    override fun onTheaterItemClick(theaterUiModel: TheaterUiModel) {
        view.startMovieReservationActivity(
            movieUiModel = movieUiModel,
            theaterUiModel = theaterUiModel
        )
    }

    override fun onAdvertisementItemClick(advertisementUiModel: AdvertisementUiModel) {
        view.startAdvertisementUrl(advertisementUiModel.url)
    }

    override fun updateMovieList() {
        val movies = MockMoviesFactory.movies
        val movieUiModels = movies.value.map { it.toUi() }
        val advertisementUiModel = MockAdvertisementFactory.generateAdvertisement()
        view.setAdapter(movieUiModels, advertisementUiModel)
    }
}
