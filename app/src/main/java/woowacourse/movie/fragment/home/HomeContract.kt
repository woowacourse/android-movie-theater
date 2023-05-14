package woowacourse.movie.fragment.home

import woowacourse.movie.Ad
import woowacourse.movie.movie.Movie

interface HomeContract {
    interface View {
        var presenter: Presenter

        fun showTheaterPicker(): (Int) -> Unit
        fun startAdDetailPage(): (ad: Ad) -> Unit
        fun setMovieList(
            movies: List<Movie>,
            ad: Ad,
            movieOnItemClicked: (Int) -> Unit,
            adOnItemClicked: (Ad) -> Unit,
        )
    }

    interface Presenter {
        fun initMovieList()
    }
}
