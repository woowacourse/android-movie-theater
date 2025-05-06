package woowacourse.movie.presentation.home.movies

import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.home.movies.adapter.MovieViewHolder

class MovieEventListener(
    private val presenter: MoviesContract.Presenter,
) : MovieViewHolder.OnMovieEventListener {
    override fun onClick(movie: MovieUiModel) {
        presenter.availableTheatersAndCount(movie.id)
    }
}
