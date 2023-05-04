package woowacourse.movie.presentation.complete

import woowacourse.movie.data.MovieData
import woowacourse.movie.presentation.mappers.toPresentation
import woowacourse.movie.presentation.model.MovieModel

class CompletePresenter(
    private val view: CompleteContract.View,
) : CompleteContract.Presenter {

    override fun requireMovieModel(): MovieModel =
        MovieData.findMovieById(view.ticketModel.movieId).toPresentation()
}
