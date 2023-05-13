package woowacourse.movie.fragment.home

import woowacourse.movie.Ad
import woowacourse.movie.movie.MovieMockData

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    override fun initMovieList() {
        view.setMovieList(
            MovieMockData.movies10000,
            Ad.dummyAd,
            view.showTheaterPicker(),
            view.startAdDetailPage()
        )
    }

}
