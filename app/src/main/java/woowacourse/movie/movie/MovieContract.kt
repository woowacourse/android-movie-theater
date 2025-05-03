package woowacourse.movie.movie

import woowacourse.movie.ui.model.MovieFeedUiModel
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

interface MovieContract {
    interface View {
        fun setupMovieList(movies: List<MovieFeedUiModel>)

        fun showTheaterDialog(
            theaters: ArrayList<TheaterUiModel>,
            movie: MovieUiModel,
        )
    }

    interface Presenter {
        fun initializeData()

        fun setTheaters(movie: MovieUiModel)
    }
}
