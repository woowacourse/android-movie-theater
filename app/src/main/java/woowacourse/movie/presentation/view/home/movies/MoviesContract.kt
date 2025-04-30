package woowacourse.movie.presentation.view.home.movies

import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheatersUiModel

interface MoviesContract {
    interface Presenter {
        fun fetchData()

        fun availableTheatersAndCount(movieId: Int)
    }

    interface View {
        fun showScreen(movies: List<MovieUiModel>)

        fun showAvailableTheatersAndCount(
            movie: MovieUiModel,
            times: TheatersUiModel,
        )
    }
}
