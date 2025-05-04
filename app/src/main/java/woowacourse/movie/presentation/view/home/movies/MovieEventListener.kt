package woowacourse.movie.presentation.view.home.movies

import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.view.home.movies.adapter.MoviesAdapter

class MovieEventListener(
    private val presenter: MoviesContract.Presenter,
) : MoviesAdapter.OnMovieEventListener {
    override fun onClick(movie: MovieUiModel) {
        presenter.availableTheatersAndCount(movie.id)
    }
}
