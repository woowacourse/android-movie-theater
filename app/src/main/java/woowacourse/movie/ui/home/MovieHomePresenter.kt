package woowacourse.movie.ui.home

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.MovieContent

class MovieHomePresenter(
    private val view: MovieHomeContract.View,
    private val movieContentDataSource: DefaultMovieDataSource<Long, MovieContent>,
) : MovieHomeContract.Presenter {
    override fun loadMovieContents() {
        view.showMovieContents(movieContentDataSource.findAll())
    }
}
