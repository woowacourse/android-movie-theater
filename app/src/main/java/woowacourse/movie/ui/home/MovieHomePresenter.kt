package woowacourse.movie.ui.home

import woowacourse.movie.data.database.movie.MovieContentDao
import woowacourse.movie.data.database.movie.MovieContentEntity
import woowacourse.movie.domain.MovieContent
import woowacourse.movie.data.mapper.toMovieContent
import java.lang.IllegalStateException
import kotlin.concurrent.thread

class MovieHomePresenter(
    private val view: MovieHomeContract.View,
    private val movieContentDataSource: MovieContentDao,
) : MovieHomeContract.Presenter {
    override fun loadMovieContents() {
        var movieContents: List<MovieContent>? = null
        thread {
            movieContents = movieContentDataSource.findAll().map(MovieContentEntity::toMovieContent)
        }.join()
        movieContents?.let { view.showMovieContents(it) } ?: view.showError(IllegalStateException())
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }
}
