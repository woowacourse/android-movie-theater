package woowacourse.movie.ui.movielist.contract

import woowacourse.movie.domain.model.MovieListItem
import woowacourse.movie.domain.model.Theater

interface MovieListContract {
    interface Presenter {
        fun loadMovieList()

        fun getMovieList(): List<MovieListItem.MovieItem>

        fun getAdvertisementList(): List<MovieListItem.AdItem>
    }

    interface View {
        fun setMoveListItems(items: List<MovieListItem>)

        fun startBookingActivity(theater: Theater)
    }
}
