package woowacourse.movie.home.presenter.contract

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie

interface MovieHomeContract {
    interface View {
        fun displayMovies(
            movies: List<Movie>,
            advertisements: List<Advertisement>,
        )

        fun displayTheaterSelectionDialog(id: Long)
    }

    interface Presenter {
        fun loadMovies()
    }
}
