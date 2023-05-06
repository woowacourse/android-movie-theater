package woowacourse.movie.presentation.complete

import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.model.data.storage.MovieStorage

class CompletePresenter(
    override val view: CompleteContract.View,
    private val movieStorage: MovieStorage
) : CompleteContract.Presenter {

    override fun getMovieById(movieId: Long): Movie = movieStorage.getMovieById(movieId)
}
