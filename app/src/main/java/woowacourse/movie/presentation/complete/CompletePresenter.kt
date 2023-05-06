package woowacourse.movie.presentation.complete

import woowacourse.movie.data.movie.MovieData

class CompletePresenter(
    private val movieData: MovieData,
    private val view: CompleteContract.View,
) : CompleteContract.Presenter {

    override fun setMovieTitle(movieId: Long) {
        val movieTitle = movieData.findMovieById(movieId).title
        view.setMovieTitle(movieTitle)
    }
}
