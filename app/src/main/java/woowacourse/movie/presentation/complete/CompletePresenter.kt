package woowacourse.movie.presentation.complete

import woowacourse.movie.data.movie.DefaultMovieData
import woowacourse.movie.data.movie.MovieData

class CompletePresenter(
    private val movieData: MovieData,
    private val view: CompleteContract.View,
) : CompleteContract.Presenter {

    override fun setMovieTitle(movieId: Long) {
        val movie = movieData.findMovieById(movieId) ?: run {
            view.setMovieTitle(DefaultMovieData.defaultMovie.title)
            return
        }
        view.setMovieTitle(movie.title)
    }
}
