package woowacourse.movie.view.main.movieslist

import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.TheatersUiModel

interface MoviesContract {
    interface Presenter {
        fun startMovieReservation(movieUiModel: MovieUiModel, theaterUiModel: TheaterUiModel)
        fun showPossibleTheatersBy(movieUiModel: MovieUiModel)
        fun showAdvertisement(advertisementUiModel: AdvertisementUiModel)
        fun updateMovieList()
    }

    interface View {
        fun showMovieList(
            movieUiModels: List<MovieUiModel>,
            advertisementUiModel: AdvertisementUiModel,
        )

        fun showBottomSheet(movieUiModel: MovieUiModel, theaters: TheatersUiModel)

        fun showMovieReservationScreen(
            movieUiModel: MovieUiModel,
            theaterUiModel: TheaterUiModel,
        )

        fun startUrl(url: String)
    }
}
