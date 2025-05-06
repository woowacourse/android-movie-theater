package woowacourse.movie.presentation.home.movies

import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.common.model.TheatersUiModel
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem

interface MoviesContract {
    interface Presenter {
        fun fetchData()

        fun availableTheatersAndCount(movieId: Int)
    }

    interface View {
        fun showScreen(movies: List<MovieMainItem>)

        fun showAvailableTheatersAndCount(
            movie: MovieUiModel,
            times: TheatersUiModel,
        )
    }
}
