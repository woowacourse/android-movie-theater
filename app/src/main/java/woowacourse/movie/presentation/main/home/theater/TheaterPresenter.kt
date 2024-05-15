package woowacourse.movie.presentation.main.home.theater

import woowacourse.movie.data.SampleTheaterData

class TheaterPresenter(private val view: TheaterContract.View) : TheaterContract.Presenter {
    override fun loadTheaters(movieId: Long) {
        val theaters = SampleTheaterData.theaters.filter { it.getCount(movieId) > 0 }
        view.showTheaters(movieId, theaters)
    }

    override fun itemClicked(movieId: Long, theaterId: Long) {
        view.navigateToDetailActivity(movieId, theaterId)
    }
}
