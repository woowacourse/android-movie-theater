package woowacourse.movie.fragment.home

import woowacourse.movie.Ad
import woowacourse.movie.movie.Movie

interface HomeContract {
    interface View {
        var presenter: Presenter

        fun startMovieDetailPage(): (position: Int) -> Unit
        fun startAdDetailPage(): (ad: Ad) -> Unit
    }

    interface Presenter {
        fun fetchMovieList(): List<Movie>
        fun fetchAd(): Ad
        fun onMovieClicked(): (Int) -> Unit
        fun onAdClicked(): (Ad) -> Unit
    }
}
