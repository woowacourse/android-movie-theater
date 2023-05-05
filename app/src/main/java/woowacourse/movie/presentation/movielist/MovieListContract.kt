package woowacourse.movie.presentation.movielist

interface MovieListContract {
    interface View {
        val presenter: Presenter
        fun setMoviesAdapter(movieItems: List<MovieItem>)
    }

    interface Presenter {
        fun requestMovies()
    }
}
