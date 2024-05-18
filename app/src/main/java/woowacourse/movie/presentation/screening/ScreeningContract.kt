package woowacourse.movie.presentation.screening

import woowacourse.movie.domain.admodel.Ad
import woowacourse.movie.presentation.uimodel.MovieUiModel

interface ScreeningContract {
    interface View {
        fun onUpdateMovies(movies: List<MovieUiModel>)

        fun onUpdateAds(ads: List<Ad>)

        fun showTheaterBottomSheet(movieId: Int)
    }

    interface Presenter {
        fun attachView(view: View)

        fun detachView()

        fun onViewSetUp()

        fun loadMovie()

        fun loadAds()

        fun onReserveButtonClicked(movieId: Int)
    }

    interface ViewActions {
        fun reserveMovie(movieId: Int)

        fun showAdContent(content: String)
    }
}
