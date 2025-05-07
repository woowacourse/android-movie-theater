package woowacourse.movie.presentation.view.movie

import woowacourse.movie.presentation.model.MovieListItem
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.TheatersUiModel

interface MovieContract {
    interface View {
        fun showMovies(items: List<MovieListItem>)

        fun showTheaterInfo(
            movie: MovieUiModel,
            theaterInfo: TheatersUiModel,
        )
    }

    interface Presenter {
        fun fetchMovies()

        fun reservationSelected(movie: MovieUiModel)
    }
}
