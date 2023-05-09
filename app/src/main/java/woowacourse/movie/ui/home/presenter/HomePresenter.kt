package woowacourse.movie.ui.home.presenter

import woowacourse.movie.model.MovieListModel
import woowacourse.movie.model.data.MockDataOfMovies
import woowacourse.movie.ui.home.HomeItemClickListener
import woowacourse.movie.ui.home.adapter.HomeAdapter

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    private val movieWithAdvertisement: List<MovieListModel>
        by lazy { MockDataOfMovies().getMoviesWithAds() }

    override fun initAdapter() {
        view.homeAdapter = HomeAdapter(movieWithAdvertisement, setEventOnClickListener())
    }

    private fun setEventOnClickListener(): HomeItemClickListener = object : HomeItemClickListener {
        override fun onMovieItemClick(movie: MovieListModel.MovieModel) {
            view.moveToDetailActivity(movie)
        }

        override fun onAdItemClick(ad: MovieListModel.AdModel) {
            view.moveToWebPage(ad)
        }
    }
}
