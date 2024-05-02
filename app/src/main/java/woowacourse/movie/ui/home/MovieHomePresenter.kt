package woowacourse.movie.ui.home

import woowacourse.movie.model.data.MovieDataSource
import woowacourse.movie.model.movie.MovieContent

class MovieHomePresenter(
    private val view: MovieHomeContract.View,
    private val movieContents: MovieDataSource<MovieContent>,
) : MovieHomeContract.Presenter {
    override fun loadMovieContents() {
        view.showMovieContents(movieContents.findAll())
    }
}
