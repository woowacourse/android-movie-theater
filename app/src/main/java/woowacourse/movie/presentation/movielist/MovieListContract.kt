package woowacourse.movie.presentation.movielist

interface MovieListContract {
    interface View {
        val presenter: Presenter

        fun updateMovieListView(movieItems: List<MovieItem>)
    }

    interface Presenter {
        val view: View

        fun getMovieItemsWithAds()
    }
}
