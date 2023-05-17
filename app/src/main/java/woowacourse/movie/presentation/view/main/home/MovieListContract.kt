package woowacourse.movie.presentation.view.main.home

import woowacourse.movie.presentation.model.Movie

interface MovieListContract {
    interface View {
        fun setRecyclerView(movies: List<Movie>)
    }

    interface Presenter {
        fun getMovieData()
    }
}