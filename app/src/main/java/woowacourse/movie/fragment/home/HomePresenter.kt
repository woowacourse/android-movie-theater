package woowacourse.movie.fragment.home

import woowacourse.movie.Ad
import woowacourse.movie.Theater
import woowacourse.movie.TheaterMockData
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieMockData

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun fetchMovieList(): List<Movie> = MovieMockData.movies10000

    override fun fetchAd(): Ad = Ad.dummyAd

    override fun fetchTheaterList(): List<Theater> = TheaterMockData.dummyTheaters

    override fun onMovieClicked(): (Int) -> Unit = view.showTheaterPicker()

    override fun onAdClicked(): (Ad) -> Unit = view.startAdDetailPage()
}
