package woowacourse.movie.presentation.movielist.movie

interface MovieListContract {
    interface View {
        val presenter: Presenter
        fun setMovieItems(movieItems: List<MovieItem>)
    }

    interface Presenter {
        fun setMovieItems()
    }
}
