package woowacourse.movie.presentation.view.movies

import woowacourse.movie.domain.model.cinema.Theater
import woowacourse.movie.presentation.model.MovieUiModel

interface MoviesContract {
    interface Presenter {
        fun fetchData()

        fun availableTheatersAndCount(movieId: Int)
    }

    interface View {
        fun showScreen(movies: List<MovieUiModel>)

        fun showAvailableTheatersAndCount(tmp: Map<Theater, Int>)
    }
}
