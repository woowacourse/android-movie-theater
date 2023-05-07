package woowacourse.movie.ui.main.movies

import domain.Movies
import woowacourse.movie.data.model.uimodel.AdvertisementUiModel
import woowacourse.movie.data.model.uimodel.MovieUiModel

interface MoviesContract {
    interface View {
        var presenter: Presenter
        fun updateMovies()
        fun setOnMovieItemClick(movieUiModel: MovieUiModel)
        fun updateAdvertisements()
        fun setOnAdvertisementItemClick(advertisementUiModel: AdvertisementUiModel)
    }

    interface Presenter {
        fun getMovies(): Movies
        fun getAdvertisements(): AdvertisementUiModel
        fun setUpClickListener()
    }
}
