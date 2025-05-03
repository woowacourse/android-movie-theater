package woowacourse.movie.ui.movielist.contract

import woowacourse.movie.domain.model.item.MovieListItem
import woowacourse.movie.domain.model.theater.Theater

interface MovieListContract {
    interface Presenter {
        fun loadMovieList()
    }

    interface View {
        fun setMoveListItems(items: List<MovieListItem>)

        fun startBookingActivity(theater: Theater)
    }
}
