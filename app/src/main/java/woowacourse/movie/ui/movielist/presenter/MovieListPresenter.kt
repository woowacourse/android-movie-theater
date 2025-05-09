package woowacourse.movie.ui.movielist.presenter

import woowacourse.movie.domain.model.item.MovieListItem.AdItem
import woowacourse.movie.domain.model.item.MovieListItem.Companion.movieListItems
import woowacourse.movie.domain.model.item.MovieListItem.MovieItem
import woowacourse.movie.sample.DUMMY_ADS
import woowacourse.movie.sample.DUMMY_MOVIES
import woowacourse.movie.ui.movielist.contract.MovieListContract

class MovieListPresenter(
    private val movieListView: MovieListContract.View,
) : MovieListContract.Presenter {
    override fun loadMovieList() {
        val movies = getMovieList()
        val ads = getAdvertisementList()
        movieListView.setMoveListItems(movieListItems(movies, ads))
    }

    private fun getMovieList(): List<MovieItem> = DUMMY_MOVIES.map { MovieItem(it) }

    private fun getAdvertisementList(): List<AdItem> = DUMMY_ADS.map { AdItem(it) }
}
