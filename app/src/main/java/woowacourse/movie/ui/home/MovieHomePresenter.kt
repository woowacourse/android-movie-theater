package woowacourse.movie.ui.home

import woowacourse.movie.model.movie.MovieContentDao
import woowacourse.movie.model.movie.MovieContentEntity
import woowacourse.movie.model.movie.toMovieContent
import kotlin.concurrent.thread

class MovieHomePresenter(
    private val view: MovieHomeContract.View,
    private val movieContentDataSource: MovieContentDao,
) : MovieHomeContract.Presenter {
    override fun loadMovieContents() {
        thread {
            val movieContents = movieContentDataSource.findAll().map(MovieContentEntity::toMovieContent)
            view.showMovieContents(movieContents)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
