package woowacourse.movie.contract

import woowacourse.movie.data.MovieListItemsViewData
import woowacourse.movie.domain.Advertisement
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.advertismentPolicy.AdvertisementPolicy

interface MovieAdapterContract {
    interface View {
        val presenter: Presenter
        fun setAdapterData(movies: MovieListItemsViewData)
    }

    interface Presenter {
        val view: View
        fun requestMovies(): List<Movie>
        fun requestAdvertisements(): List<Advertisement>
        fun requestAdvertisementPolicy(): AdvertisementPolicy
        fun setMovieList()
    }
}
