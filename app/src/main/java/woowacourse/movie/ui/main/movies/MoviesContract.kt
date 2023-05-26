package woowacourse.movie.ui.main.movies

import domain.Movies
import woowacourse.movie.data.model.uimodel.AdvertisementUIModel
import woowacourse.movie.data.model.uimodel.MovieUIModel

interface MoviesContract {
    interface View {
        var presenter: Presenter
        fun updateMovies()
        fun setOnMovieItemClick(movieUiModel: MovieUIModel)
        fun updateAdvertisements()
        fun setOnAdvertisementItemClick(advertisementUiModel: AdvertisementUIModel)
    }

    interface Presenter {
        fun getMovies(): Movies
        fun getAdvertisements(): AdvertisementUIModel
        fun setUpClickListener()
    }
}
