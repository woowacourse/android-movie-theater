package woowacourse.movie.view.main.home

import woowacourse.movie.model.movie.Movie

interface MovieContract {
    interface View {
        fun showMovies(movies: List<Movie>)

        fun showBottomSheetDialog(movie: Movie)
    }

    interface Presenter {
        fun fetchMovies()
    }
}
