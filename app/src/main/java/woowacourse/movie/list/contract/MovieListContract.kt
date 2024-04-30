package woowacourse.movie.list.contract

import android.os.Bundle
import woowacourse.movie.list.model.Advertisement
import woowacourse.movie.list.model.Movie

interface MovieListContract {
    interface View {
        val presenter: Presenter

        fun showMoviesInfo(
            movies: List<Movie>,
            advertisements: List<Advertisement>,
        )

        fun setOnListViewClickListener(savedInstanceState: Bundle?)
    }

    interface Presenter {
        val movieList: List<Movie>

        fun setMoviesInfo()

        fun setListViewClickListenerInfo(savedInstanceState: Bundle?)
    }
}
