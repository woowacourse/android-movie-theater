package woowacourse.movie.view.movie

import woowacourse.movie.model.Movie

interface MovieContract {
    interface View {
        fun showMovies(movies: List<Movie>)

        fun showBottomSheetDialog(movie: Movie)
    }

    interface Presenter {
        fun fetchMovies()
    }
}
