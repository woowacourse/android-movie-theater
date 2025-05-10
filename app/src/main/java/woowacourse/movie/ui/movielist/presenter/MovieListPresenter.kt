package woowacourse.movie.ui.movielist.presenter

import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.dummy.DUMMY_ADS
import woowacourse.movie.data.mapper.MovieMapper
import woowacourse.movie.domain.model.item.MovieListItem.AdItem
import woowacourse.movie.domain.model.item.MovieListItem.Companion.movieListItems
import woowacourse.movie.domain.model.item.MovieListItem.MovieItem
import woowacourse.movie.ui.movielist.contract.MovieListContract
import kotlin.concurrent.thread

class MovieListPresenter(
    private val movieListView: MovieListContract.View,
    private val appDatabase: AppDatabase,
) : MovieListContract.Presenter {
    override fun loadMovieList() {
        thread {
            val movies: List<MovieItem> = getMovieList()
            val ads: List<AdItem> = getAdvertisementList()
            movieListView.setMoveListItems(movieListItems(movies, ads))
        }
    }

    private fun getMovieList(): List<MovieItem> = appDatabase.movieDao().getAll().map { MovieItem(MovieMapper.toModel(it)) }

    private fun getAdvertisementList(): List<AdItem> = DUMMY_ADS.map { AdItem(it) }
}
