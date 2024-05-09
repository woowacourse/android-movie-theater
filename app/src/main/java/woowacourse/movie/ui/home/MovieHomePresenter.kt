package woowacourse.movie.ui.home

import woowacourse.movie.model.data.DefaultMovieDataSource
import woowacourse.movie.model.movie.MovieContent
import woowacourse.movie.model.movie.MovieContentDao
import woowacourse.movie.model.movie.MovieContentEntity
import woowacourse.movie.model.movie.toMovieContent

class MovieHomePresenter(
    private val view: MovieHomeContract.View,
    private val movieContentDataSource: MovieContentDao,
) : MovieHomeContract.Presenter {
    override fun loadMovieContents() {
        Thread {
            val movieContents = movieContentDataSource.findAll().map(MovieContentEntity::toMovieContent)
            view.showMovieContents(movieContents)
        }.start()
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
