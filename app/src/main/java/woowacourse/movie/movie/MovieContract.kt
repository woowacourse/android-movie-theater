package woowacourse.movie.movie

import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

interface MovieContract {
    interface View {
        fun setupMovieList(movies: List<MovieUiModel>)

        fun showToast(message: String)

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
