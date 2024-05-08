package woowacourse.movie.presentation.main.home

import woowacourse.movie.data.SampleAdvertisementData
import woowacourse.movie.data.SampleMovieData

class HomePresenter(
    val view: HomeContract.View,
) : HomeContract.Presenter {
    override val movieList = SampleMovieData.movieList
    private val advertisementList = SampleAdvertisementData.advertisementList

    override fun setMoviesInfo() {
        view.showMoviesInfo(movieList, advertisementList)
    }

    override fun setListViewClickListenerInfo() {
        view.setOnListViewClickListener()
    }
}
