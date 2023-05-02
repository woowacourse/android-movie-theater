package woowacourse.movie.contract

import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel

interface MoviesContract {
    interface View {
        val presenter: Presenter
        fun setAdapter(
            movieUiModels: List<MovieUiModel>,
            advertisementUiModel: AdvertisementUiModel
        )

        fun startMovieReservationActivity(
            movieUiModel: MovieUiModel
        )

        fun startAdvertisementUrl(url: String)
    }

    interface Presenter {
        fun onMovieItemClick(movieUiModel: MovieUiModel)
        fun onAdvertisementItemClick(advertisementUiModel: AdvertisementUiModel)
        fun updateMovieList()
    }
}
