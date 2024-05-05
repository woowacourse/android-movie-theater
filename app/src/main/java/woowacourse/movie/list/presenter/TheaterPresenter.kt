package woowacourse.movie.list.presenter

import woowacourse.movie.list.view.TheaterFragmentContract

class TheaterPresenter(private val view: TheaterFragmentContract.View) :
    TheaterFragmentContract.Presenter {
    override fun itemClicked(movieId: Long, theaterId: Long) {
        view.navigateToDetailActivity(movieId, theaterId)
    }
}
