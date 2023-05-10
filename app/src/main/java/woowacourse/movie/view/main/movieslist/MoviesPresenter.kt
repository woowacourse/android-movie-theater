package woowacourse.movie.view.main.movieslist

import woowacourse.movie.mock.MockAdvertisementFactory
import woowacourse.movie.mock.MockMoviesFactory
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.mapper.MovieMapper.toUi

class MoviesPresenter(val view: MoviesContract.View) : MoviesContract.Presenter {
    override fun showPossibleTheatersBy(clickedMovie: MovieUiModel) {
        view.showBottomSheet(clickedMovie, clickedMovie.theaters)
    }

    override fun startMovieReservation(movieUiModel: MovieUiModel, theaterUiModel: TheaterUiModel) {
        view.showMovieReservationScreen(
            movieUiModel = movieUiModel,
            theaterUiModel = theaterUiModel,
        )
    }

    override fun showAdvertisement(advertisementUiModel: AdvertisementUiModel) {
        view.startUrl(advertisementUiModel.url)
    }

    override fun updateMovieList() {
        val movies = MockMoviesFactory.movies
        val movieUiModels = movies.value.map { it.toUi() }
        val advertisementUiModel = MockAdvertisementFactory.generateAdvertisement()
        view.showMovieList(movieUiModels, advertisementUiModel)
    }
}
