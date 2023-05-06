package woowacourse.movie.presentation.movielist.movie

interface MovieListContract {
    interface View {
        val presenter: Presenter
        fun setMoviesAdapter(movieItems: List<MovieItem>)
    }

    interface Presenter {
        fun setMovies()
    }
}
