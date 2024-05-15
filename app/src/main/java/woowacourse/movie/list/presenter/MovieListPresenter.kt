package woowacourse.movie.list.presenter

import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.list.contract.MovieListContract

class MovieListPresenter(
    val view: MovieListContract.View,
) : MovieListContract.Presenter {
    private val theaterContent = CommonDataSource.theaterContent

    override fun setMoviesInfo() {
        view.showMoviesList()
    }

    override fun updateMoviesInfo() {
        view.updateMovieItems(theaterContent)
    }

    override fun setMovieListAdapter() {
        view.linkMovieListAdapter(theaterContent)
    }
}
