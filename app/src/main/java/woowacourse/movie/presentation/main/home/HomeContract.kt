package woowacourse.movie.presentation.main.home

import woowacourse.movie.domain.model.home.Advertisement
import woowacourse.movie.domain.model.home.Movie

interface HomeContract {
    interface View {
        val presenter: Presenter

        fun showMoviesInfo(
            movies: List<Movie>,
            advertisements: List<Advertisement>,
        )

        fun setOnListViewClickListener()
    }

    interface Presenter {
        val movieList: List<Movie>

        fun setMoviesInfo()

        fun setListViewClickListenerInfo()
    }
}
