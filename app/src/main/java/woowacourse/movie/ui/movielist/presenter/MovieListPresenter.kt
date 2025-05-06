package woowacourse.movie.ui.movielist.presenter

import woowacourse.movie.domain.model.MovieListItem
import woowacourse.movie.sample.DUMMY_ADS
import woowacourse.movie.sample.DUMMY_MOVIES
import woowacourse.movie.ui.movielist.contract.MovieListContract

class MovieListPresenter(
    private val movieListView: MovieListContract.View,
) : MovieListContract.Presenter {
    override fun loadMovieList() {
        val movies = movieItems()
        val ads = advertisementItems()
        movieListView.showMoveListItems(movieListItems(movies, ads))
    }

    override fun startBooking(movieId: Long) {
        movieListView.showTheaters(movieId)
    }

    private fun movieItems(): List<MovieListItem.MovieItem> =
        DUMMY_MOVIES.values.map { movie ->
            MovieListItem.MovieItem(movie)
        }

    private fun advertisementItems(): ArrayDeque<MovieListItem.AdItem> =
        DUMMY_ADS.map {
            MovieListItem.AdItem(it)
        }.toCollection(ArrayDeque())

    private fun movieListItems(
        movies: List<MovieListItem.MovieItem>,
        ads: ArrayDeque<MovieListItem.AdItem>,
    ): List<MovieListItem> {
        var insertItemCount = 0
        var curAds = ads.removeFirst()
        return buildList {
            movies.forEach { movie ->
                add(movie)
                ++insertItemCount
                if (curAds.advertisement.isInsertAdvertise(insertItemCount)) {
                    add(curAds)

                    ads.addLast(curAds)
                    curAds = ads.removeFirst()
                    insertItemCount = 0
                }
            }
        }
    }
}
