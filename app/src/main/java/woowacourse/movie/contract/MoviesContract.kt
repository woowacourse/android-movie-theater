package woowacourse.movie.contract

import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.TheatersUiModel

interface MoviesContract {
    interface View {
        val presenter: Presenter
        fun setAdapter(
            movieUiModels: List<MovieUiModel>,
            advertisementUiModel: AdvertisementUiModel
        )

        fun showBottomSheet(theaters: TheatersUiModel)

        fun startMovieReservationActivity(
            movieUiModel: MovieUiModel, theaterUiModel: TheaterUiModel
        )

        fun startAdvertisementUrl(url: String)
    }

    interface Presenter {
        fun onTheaterItemClick(theaterUiModel: TheaterUiModel)
        fun onMovieItemClick(movieUiModel: MovieUiModel)
        fun onAdvertisementItemClick(advertisementUiModel: AdvertisementUiModel)
        fun updateMovieList()
    }
}
