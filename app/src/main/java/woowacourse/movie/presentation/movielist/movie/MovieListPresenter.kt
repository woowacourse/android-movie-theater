package woowacourse.movie.presentation.movielist.movie

import woowacourse.movie.data.movieitem.MovieItemData

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieItemData: MovieItemData,
) : MovieListContract.Presenter {

    override fun setMovieItems() {
        view.setMovieItems(movieItemData.getMovieItems())
    }
}
