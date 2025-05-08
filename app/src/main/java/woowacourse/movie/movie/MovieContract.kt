package woowacourse.movie.movie

import woowacourse.movie.domain.Movie

interface MovieContract {
    interface View {
        fun showMovies(movies: List<FeedItem>)

        fun navigateToBook(movie: Movie)

        fun navigateToAdPage()

        fun showError(messageResId: Int)
    }

    interface Presenter {
        fun loadMovies()

        fun selectedMovie(movie: Movie)

        fun selectedAd()
    }
}
