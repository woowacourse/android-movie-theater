package woowacourse.movie.fragment.home

import woowacourse.movie.Ad
import woowacourse.movie.Theater
import woowacourse.movie.movie.Movie

interface HomeContract {
    interface View {
        var presenter: Presenter

        fun showTheaterPicker(): (Int) -> Unit
        fun startAdDetailPage(): (ad: Ad) -> Unit
    }

    interface Presenter {
        fun fetchMovieList(): List<Movie>
        fun fetchTheaterList(): List<Theater>
        fun fetchAd(): Ad
        fun onMovieClicked(): (Int) -> Unit
        fun onAdClicked(): (Ad) -> Unit
    }
}
