package woowacourse.movie.ui.movielist.contract

import woowacourse.movie.domain.model.MovieListItem

interface MovieListContract {
    interface Presenter {
        fun loadMovieList()

        fun startBooking(movieId: Long)
    }

    interface View {
        fun showMoveListItems(items: List<MovieListItem>)

        fun showTheaters(movieId: Long)
    }
}
