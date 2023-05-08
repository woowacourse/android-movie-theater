package woowacourse.movie.ui.home.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import woowacourse.movie.model.MovieListModel
import woowacourse.movie.model.MovieListModel.AdModel
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.model.data.MockData
import woowacourse.movie.ui.home.HomeItemClickListener
import woowacourse.movie.ui.home.adapter.HomeAdapter
import woowacourse.movie.ui.moviedetail.MovieDetailActivity

class HomePresenter(
    private val view: HomeContract.View,
    private val homeFragmentContext: Context,
) : HomeContract.Presenter {
    private val movieWithAdvertisement: List<MovieListModel>
        by lazy { MockData().getMoviesWithAds() }

    override fun initAdapter() {
        view.homeAdapter = HomeAdapter(movieWithAdvertisement, setEventOnClickListener())
    }

    private fun setEventOnClickListener(): HomeItemClickListener = object : HomeItemClickListener {
        override fun onMovieItemClick(movie: MovieModel) {
            moveToDetailActivity(movie)
        }

        override fun onAdItemClick(ad: AdModel) {
            moveToWebPage(ad)
        }
    }

    private fun moveToDetailActivity(movie: MovieModel) {
        val intent =
            Intent(homeFragmentContext, MovieDetailActivity::class.java).apply {
                putExtra(KEY_MOVIE, movie)
            }

        homeFragmentContext.startActivity(intent)
    }

    private fun moveToWebPage(ad: AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
        homeFragmentContext.startActivity(intent)
    }

    companion object {
        const val KEY_MOVIE = "movie"
    }
}
