package woowacourse.movie.ui.home.presenter

import woowacourse.movie.model.MovieListModel
import woowacourse.movie.model.data.MockData

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    private val movieWithAdvertisement: List<MovieListModel>
        by lazy { MockData().getMoviesWithAds() }

    override fun getMovieWithAdvertisement() {
        view.movieWithAdvertisement = movieWithAdvertisement
    }
}
