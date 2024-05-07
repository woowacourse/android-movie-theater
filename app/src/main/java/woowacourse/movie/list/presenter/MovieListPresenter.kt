package woowacourse.movie.list.presenter

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.list.contract.MovieListContract

class MovieListPresenter(
    val view: MovieListContract.View,
) : MovieListContract.Presenter {
    private val theaterContent = MovieDataSource.theaterContent

    override fun setMoviesInfo() {
        view.showMoviesInfo()
    }

    override fun updateMoviesInfo() {
        view.updateMovieEntity(theaterContent)
    }

    override fun setMovieListAdapter() {
        view.makeMovieListAdapter(theaterContent)
    }
}
